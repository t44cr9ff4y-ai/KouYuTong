package com.kuayutong.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kuayutong.R
import com.kuayutong.databinding.ActivityMainBinding
import com.kuayutong.ui.practice.PracticeFragment
import com.kuayutong.ui.progress.ProgressFragment
import com.kuayutong.ui.topics.TopicsFragment

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupBottomNavigation()
        
        // Load default fragment
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_practice
        }
    }
    
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_practice -> PracticeFragment()
                R.id.nav_topics -> TopicsFragment()
                R.id.nav_progress -> ProgressFragment()
                else -> null
            }
            
            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, it)
                    .commit()
                true
            } ?: false
        }
    }
}
