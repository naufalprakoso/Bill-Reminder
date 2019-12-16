package com.naufalprakoso.billreminder.ui.main.paid


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.naufalprakoso.billreminder.R

/**
 * A simple [Fragment] subclass.
 */
class BillPaidFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return BillPaidFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill_paid, container, false)
    }

}
