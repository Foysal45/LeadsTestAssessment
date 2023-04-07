package com.example.leadsassessment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.leadsassessment.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var navigationMenuId: Int = 0
    private var menuItem: MenuItem? = null


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        navView = findViewById(R.id.nav_view)
        setSupportActionBar(binding.appBarHome.toolbar)
        navController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_dashboard), binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)


        manageNavigationItemSelection()

    }



    companion object {
        const val TAG = "MainActivity"
    }

    //DrawerNavigation Item Click
    private fun manageNavigationItemSelection() {
        binding.navView.setNavigationItemSelectedListener { item ->
            navigationMenuId = item.itemId
            menuItem = item
            val handled = NavigationUI.onNavDestinationSelected(item, navController)
            if (handled) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)

            } else {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
                when (menuItem!!.itemId) {
                    R.id.nav_home -> {
                        onBackPressed()
                    }

                    R.id.nav_profile -> {
                        navController.navigate((R.id.profileFragment))
                    }


                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    //function for set the Toolbar Title under the Fragment of this Main Activity
    fun setActionBarTitle(title: String) {
        binding.appBarHome.tvTitleToolbar.visibility = View.VISIBLE
        binding.appBarHome.tvTitleToolbar.text = title
        supportActionBar?.title = title
    }


    //function for double click in back button to exit the app
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        when {
            //back button single click to close Navigation Drawer
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) || binding.drawerLayout.isDrawerOpen(GravityCompat.END) -> {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.END)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.END)
                }
            }

            navController.currentDestination?.id != navController.graph.startDestination -> {
                super.onBackPressed()
            }

            else -> {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed()
                    return
                }
                doubleBackToExitPressedOnce = true
                Toast.makeText(this, getString(R.string.press_again_to_exit), Toast.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    doubleBackToExitPressedOnce = false
                }, 2000L)
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}