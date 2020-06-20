package com.naufalprakoso.billreminder.ui.main.unpaid

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.FragmentBillUnpaidBinding
import com.naufalprakoso.billreminder.ui.bill.detail.BillDetailActivity
import com.naufalprakoso.billreminder.utils.BILL_ID

class BillUnpaidFragment : Fragment() {

    private val bills = arrayListOf<Bill>()

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val handler = Handler()

    private lateinit var unpaidAdapter: BillUnpaidAdapter
    private lateinit var binding: FragmentBillUnpaidBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillUnpaidBinding.inflate(inflater, container, false)

        binding.rvBills.setHasFixedSize(true)
        binding.rvBills.layoutManager = LinearLayoutManager(context)
        binding.rvBills.adapter = unpaidAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            dbWorkerThread = DbWorkerThread("dbWorkerThread")
            dbWorkerThread.start()

            if (context != null) {
                db = AppDatabase.getInstance(context!!)

                unpaidAdapter =
                    BillUnpaidAdapter(context!!, { bill, isChecked ->
                        if (isChecked) {
                            Toast.makeText(context, "Bill has been paid", Toast.LENGTH_SHORT).show()

                            val newBill = bill.copy(paid = "true")
                            updateBill(newBill)

                            getBillData()
                        }
                    }, { bill ->
                        val intent = Intent(context, BillDetailActivity::class.java)
                        intent.putExtra(BILL_ID, bill)
                        startActivity(intent)
                    })
            }
        }
    }

    private fun updateBill(bill: Bill) {
        val task = Runnable { db?.billDao()?.update(bill) }
        dbWorkerThread.postTask(task)
    }

    override fun onStart() {
        super.onStart()

        getBillData()
    }

    private fun getBillData() {
        bills.clear()
        val task = Runnable {
            val billData = db?.billDao()?.getUnpaidBills()
            handler.post {
                billData?.let { bills.addAll(it) }
                if (bills.isEmpty()) {
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.rvBills.visibility = View.GONE
                } else {
                    binding.tvNoData.visibility = View.GONE
                    binding.rvBills.visibility = View.VISIBLE
                }
                unpaidAdapter.setBills(bills)
                unpaidAdapter.notifyDataSetChanged()
            }
        }
        dbWorkerThread.postTask(task)
    }

    companion object {
        fun newInstance(): Fragment {
            return BillUnpaidFragment()
        }
    }

}
