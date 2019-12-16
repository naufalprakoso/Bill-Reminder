package com.naufalprakoso.billreminder.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.naufalprakoso.billreminder.ui.main.all.BillsFragment
import com.naufalprakoso.billreminder.ui.main.paid.BillPaidFragment
import com.naufalprakoso.billreminder.ui.main.unpaid.BillUnpaidFragment

class TabsMainAdapter(
    fm: FragmentManager,
    private var mNumOfTabs: Int
) : FragmentStatePagerAdapter(fm) {

    override fun getCount(): Int {
        return mNumOfTabs
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BillUnpaidFragment.newInstance()
            1 -> BillPaidFragment.newInstance()
            else -> BillsFragment.newInstance()
        }
    }
}