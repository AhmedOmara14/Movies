package com.illa.task.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.illa.task.R
import com.illa.task.databinding.ActivityMainBinding
import com.illa.task.presentation.all_movies.AllMoviesFragment
import com.illa.task.presentation.wishlist.WishlistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initNavigationView()
    }

    private fun initNavigationView() {
        val navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(binding.bottomNavigationView, navController)

        loadFragment(AllMoviesFragment())
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            var fragment: Fragment? = null

            @SuppressLint("NonConstantResourceId")
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                when (item.itemId) {
                    R.id.navigation_home -> fragment = AllMoviesFragment()
                    R.id.navigation_wishlist -> fragment = WishlistFragment()
                     }
                return loadFragment(fragment)
            }
        })

    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
            return true
        }
        return false
    }
}