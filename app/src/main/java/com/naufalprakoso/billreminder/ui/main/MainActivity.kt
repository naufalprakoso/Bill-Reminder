package com.naufalprakoso.billreminder.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.appcompat.app.AppCompatActivity
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.ui.bill.add.AddBillActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.menu_bills_unpaid)))
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.menu_bills_paid)))
        tab_layout.addTab(tab_layout.newTab().setText(getString(R.string.menu_bills)))

        TabsMainAdapter(supportFragmentManager, 0)

        val tabsAdapter = TabsMainAdapter(supportFragmentManager, tab_layout.tabCount)
        view_pager.adapter = tabsAdapter
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))

        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                view_pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        fab.setOnClickListener {
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
        }
    }
}