package com.naufalprakoso.billreminder.ui.main.unpaid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.FragmentBillUnpaidBinding
import com.naufalprakoso.billreminder.ui.bill.detail.BillDetailActivity
import com.naufalprakoso.billreminder.utils.BILL_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillUnpaidFragment : Fragment() {

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread

    private lateinit var unpaidAdapter: BillUnpaidAdapter
    private lateinit var binding: FragmentBillUnpaidBinding

    private val billViewModel: BillUnpaidViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillUnpaidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null && context != null) {
            initAdapter()
            binding.rvBills.setHasFixedSize(true)
            binding.rvBills.layoutManager = LinearLayoutManager(context)
            binding.rvBills.adapter = unpaidAdapter

            billViewModel.getBills().observe(viewLifecycleOwner, Observer {
                renderBillData(it)
            })
        }
    }

    private fun updateBill(bill: Bill) {
        val task = Runnable { db?.billDao()?.update(bill) }
        dbWorkerThread.postTask(task)
        unpaidAdapter.notifyDataSetChanged()
    }

    private fun initAdapter() {
        unpaidAdapter =
            BillUnpaidAdapter(requireContext(), { bill, isChecked ->
                if (isChecked) {
                    Toast.makeText(context, "Bill has been paid", Toast.LENGTH_SHORT).show()

                    val newBill = bill.copy(paid = "true")
                    billViewModel.updateBills(newBill)

                }
            }, { bill ->
                val intent = Intent(context, BillDetailActivity::class.java)
                intent.putExtra(BILL_ID, bill)
                startActivity(intent)
            })
    }

    private fun renderBillData(bills: List<Bill>) {
        if (bills.isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
            binding.rvBills.visibility = View.GONE
        } else {
            binding.tvNoData.visibility = View.GONE
            binding.rvBills.visibility = View.VISIBLE
        }
        setBillData(bills)
    }

    private fun setBillData(bills: List<Bill>) {
        unpaidAdapter.setBills(bills)
        unpaidAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): Fragment {
            return BillUnpaidFragment()
        }
    }

}
