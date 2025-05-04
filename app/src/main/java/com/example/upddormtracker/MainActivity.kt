package com.example.upddormtracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.upddormtracker.databinding.ActivityMainBinding
import com.example.upddormtracker.ui.login.LoginActivity
import com.example.upddormtracker.ui.scan_id.ScanIDFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater

        val graph = navInflater.inflate(R.navigation.mobile_navigation)

        getUser()

        //TODO REMOVE THIS AFTER LOL
        val isDeveloping = true

        if (isDeveloping) {
            navView.menu.add(Menu.NONE, R.id.home_admin, 0, "Home (admin)")
            navView.menu.add(Menu.NONE, R.id.nav_manage_dormers, 0, "Manage Dormers")
            navView.menu.add(Menu.NONE, R.id.dormDetailsFragment, 0, "Manage Dorm Details")
            navView.menu.add(Menu.NONE, R.id.dashboardDormerFragment, 0, "Home (dormer)")
            navView.menu.add(Menu.NONE, R.id.nav_requests, 0, "Create a Request")
            navView.menu.add(Menu.NONE, R.id.adminProfileFragment, 0, "Admin Profile")
        } else if (isAdmin) {
            navView.menu.add(Menu.NONE, R.id.home_admin, 0, "Home")
            navView.menu.add(Menu.NONE, R.id.nav_manage_dormers, 0, "Manage Dormers")
            navView.menu.add(Menu.NONE, R.id.dormDetailsFragment, 0, "Manage Dorm Details")
        } else {
            navView.menu.add(Menu.NONE, R.id.dashboardDormerFragment, 0, "Home")
            navView.menu.add(Menu.NONE, R.id.nav_requests, 0, "Create a Request")
        }

        // Dynamically set start destination
        graph.setStartDestination(if (isAdmin) R.id.home_admin else R.id.dashboardDormerFragment)
        navController.graph = graph

        // Dynamically set top-level destinations
        val topLevelDestinations = if (isAdmin) {
            setOf(R.id.home_admin, R.id.dormDetailsFragment)
        } else {
            setOf(R.id.dashboardDormerFragment, R.id.nav_requests)
        }

        appBarConfiguration = AppBarConfiguration(topLevelDestinations, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Highlight correct menu item
        navView.setCheckedItem(if (isAdmin) R.id.home_admin else R.id.dashboardDormerFragment)


    }

    private val userViewModel: UserViewModel by viewModels()

    fun getUser() {
        val user = Firebase.auth.currentUser
        user?.let {
            Firebase.firestore.collection("users").document(it.uid).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val dorm = document.getString("dorm") ?: ""
                        userViewModel.setDorm(dorm) // Store dorm info in ViewModel
                        val isAdminL = document.getBoolean("isAdmin") ?: false
                        if (isAdminL) {
                            userViewModel.setIsAdmin(true)
                            isAdmin = true
                        } else {
                            userViewModel.setIsAdmin(false)
                            isAdmin = false
                        }
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_logOut -> {
                // Handle logout button click
                logout() // Call your logout function
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        val auth = Firebase.auth
        auth.signOut()

        val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
        googleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
            ?.childFragmentManager
            ?.fragments
            ?.firstOrNull()

        if (currentFragment is ScanIDFragment) {
            currentFragment.handleTagIntent(intent)
        }
    }


}