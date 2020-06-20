package com.naufalprakoso.billreminder.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.databinding.ActivityMainBinding
import com.naufalprakoso.billreminder.ui.bill.add.AddBillActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.menu_bills_unpaid)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.menu_bills_paid)))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText(getString(R.string.menu_bills)))

        TabsMainAdapter(supportFragmentManager, 0)

        val tabsAdapter = TabsMainAdapter(supportFragmentManager, binding.tabLayout.tabCount)
        binding.viewPager.adapter = tabsAdapter
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}