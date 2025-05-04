package com.example.upddormtracker

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var isAdmin: Boolean = false
    private var isDormer: Boolean = false


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
    }

    private val userViewModel: UserViewModel by viewModels()

    fun getUser() {
        val user = Firebase.auth.currentUser
        if (user == null) return

        val timeoutMillis: Long = 5000 // 5 seconds timeout
        var didRespond = false

        val timeoutHandler = Handler(Looper.getMainLooper())
        val timeoutRunnable = Runnable {
            if (!didRespond) {
                Log.e("Firestore", "Request timed out. Probably no internet.")
                showNoInternetDialog()
            }
        }
        timeoutHandler.postDelayed(timeoutRunnable, timeoutMillis)

        Firebase.firestore.collection("users").document(user.uid).get()
            .addOnSuccessListener { document ->
                didRespond = true
                timeoutHandler.removeCallbacks(timeoutRunnable)

                if (document.exists()) {
                    val dorm = document.getString("dorm") ?: ""
                    userViewModel.setDorm(dorm)
                    isAdmin = document.getBoolean("isAdmin") ?: false
                    userViewModel.setIsAdmin(isAdmin)
                    isDormer = document.getBoolean("isDormer") ?: false
                    userViewModel.setIsDormer(isDormer)

                    if (!isDormer && !isAdmin) {
                        Toast.makeText(
                            this,
                            "Please contact your Dorm Manager to access the app's features",
                            Toast.LENGTH_SHORT
                        ).show()
                        logout()
                    } else {
                        setupNavigation()
                    }
                }
            }
            .addOnFailureListener { e ->
                didRespond = true
                timeoutHandler.removeCallbacks(timeoutRunnable)
                Log.e("Firestore", "Error fetching user: ${e.message}")
                showNoInternetDialog()
            }
    }


    fun setupNavigation() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        val navInflater = navController.navInflater

        val graph = navInflater.inflate(R.navigation.mobile_navigation)

        if (isAdmin) {
            navView.menu.add(Menu.NONE, R.id.home_admin, 0, "Home")
            navView.menu.add(Menu.NONE, R.id.manageRequestsFragment, 0, "Manage Requests")
            navView.menu.add(Menu.NONE, R.id.nav_manage_dormers, 0, "Manage Dormers")
            navView.menu.add(Menu.NONE, R.id.dormDetailsFragment, 0, "Manage Dorm Details")
            graph.setStartDestination(R.id.home_admin)
        } else if (isDormer) {
            navView.menu.add(Menu.NONE, R.id.dashboardDormerFragment, 0, "Home")
            navView.menu.add(Menu.NONE, R.id.nav_requests, 0, "Create a Request")
            graph.setStartDestination(R.id.dashboardDormerFragment)
        } else {
            Toast.makeText(
                this,
                "Please contact your Dorm Manager to access the app's features",
                Toast.LENGTH_SHORT
            ).show()
            logout()
        }

        navController.graph = graph

        val topLevelDestinations = if (isAdmin) {
            setOf(R.id.home_admin, R.id.dormDetailsFragment, R.id.nav_manage_dormers, R.id.manageRequestsFragment)
        } else if (isDormer) {
            setOf(R.id.dashboardDormerFragment, R.id.nav_requests)
        } else {
            setOf()
        }

        appBarConfiguration = AppBarConfiguration(topLevelDestinations, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setCheckedItem(if (isAdmin) R.id.home_admin else R.id.dashboardDormerFragment)
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

    fun showNoInternetDialog() {
        AlertDialog.Builder(this)
            .setTitle("Connection Error")
            .setMessage("Please check your internet connection and try again.")
            .setPositiveButton("Retry") { _, _ -> getUser() }
            .setNegativeButton("Exit") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }
}