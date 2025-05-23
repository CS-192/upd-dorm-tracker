package com.example.upddormtracker

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.upddormtracker.databinding.ActivityMainBinding
import com.example.upddormtracker.ui.login.LoginActivity
import com.example.upddormtracker.ui.managedormers.RegisterRFIDFragment
import com.example.upddormtracker.ui.scan_id.ScanIDFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

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

        getUser()
    }

    private val userViewModel: UserViewModel by viewModels()

    fun updateSidebar(user: FirebaseUser) {
        val headerView = binding.navView.getHeaderView(0)
        val nameTextView = headerView.findViewById<TextView>(R.id.nav_header_name)
        val emailTextView = headerView.findViewById<TextView>(R.id.nav_header_email)
        val profileImageView = headerView.findViewById<ImageView>(R.id.imageView)

        nameTextView.text = user.displayName ?: "No name"
        emailTextView.text = user.email ?: "No email"

        val photoUrl = user.photoUrl
        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .placeholder(R.mipmap.ic_launcher_round)
                .circleCrop()
                .into(profileImageView)
        }

    }

    fun getUser() {
        val user = Firebase.auth.currentUser
        if (user == null) return
        updateSidebar(user)

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
                    val studentNumber = document.getString("studentNumber") ?: ""
                    userViewModel.setStudentNumber(studentNumber)
                    val name = document.getString("name") ?: ""
                    userViewModel.setName(name)
                    val email = document.getString("email") ?: ""
                    userViewModel.setEmail(email)

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
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        if (navHostFragment !is NavHostFragment) {
            Log.e("MainActivity", "NavHostFragment not found.")
            return
        }

        val navController = navHostFragment.navController
        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.mobile_navigation)

        // Clear previous menu items to avoid duplication or residue
        navView.menu.clear()

        val topLevelDestinations: Set<Int>

        if (isAdmin) {
            navView.menu.add(Menu.NONE, R.id.home_admin, Menu.NONE, "Home")
            navView.menu.add(Menu.NONE, R.id.manageRequestsFragment, Menu.NONE, "Manage Requests")
            navView.menu.add(Menu.NONE, R.id.nav_manage_dormers, Menu.NONE, "Manage Dormers")
            navView.menu.add(Menu.NONE, R.id.dormDetailsFragment, Menu.NONE, "Manage Dorm Details")
            navView.menu.add(Menu.NONE, R.id.entryExitLogsFragment, Menu.NONE, "Entry and Exit Logs")

            graph.setStartDestination(R.id.home_admin)

            topLevelDestinations = setOf(
                R.id.home_admin,
                R.id.manageRequestsFragment,
                R.id.nav_manage_dormers,
                R.id.dormDetailsFragment
            )

        } else if (isDormer) {
            navView.menu.add(Menu.NONE, R.id.dashboardDormerFragment, Menu.NONE, "Home")
            navView.menu.add(Menu.NONE, R.id.nav_requests, Menu.NONE, "Create a Request")
            graph.setStartDestination(R.id.dashboardDormerFragment)

            topLevelDestinations = setOf(
                R.id.dashboardDormerFragment,
                R.id.nav_requests
            )

        } else {
            Toast.makeText(
                this,
                "Please contact your Dorm Manager to access the app's features",
                Toast.LENGTH_SHORT
            ).show()
            logout()
            return
        }

        navController.graph = graph

        appBarConfiguration = AppBarConfiguration(topLevelDestinations, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Set the default checked item safely AFTER items are added
        val defaultItem = if (isAdmin) R.id.home_admin else R.id.dashboardDormerFragment
        navView.setCheckedItem(defaultItem)
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

            R.id.action_profile -> {
                val navController = findNavController(R.id.nav_host_fragment_content_main)
                if (isAdmin) {
                    navController.navigate(R.id.adminProfileFragment)
                } else if (isDormer) {
                    navController.navigate(R.id.dormerProfileFragment)
                }
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
    @RequiresApi(Build.VERSION_CODES.O)
    
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
                ?.childFragmentManager
                ?.fragments
                ?.firstOrNull()

        when (currentFragment) {
            is ScanIDFragment -> {
                currentFragment.handleTagIntent(intent)
            }
            is RegisterRFIDFragment -> {
                currentFragment.handleNfcIntent(intent)
            }
        }
    }


}