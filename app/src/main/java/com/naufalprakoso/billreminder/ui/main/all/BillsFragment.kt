package com.naufalprakoso.billreminder.ui.main.all

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.FragmentBillsBinding
import com.naufalprakoso.billreminder.ui.bill.detail.BillDetailActivity
import com.naufalprakoso.billreminder.utils.BILL_ID

class BillsFragment : Fragment() {

    private val bills = arrayListOf<Bill>()

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val handler = Handler()

    private lateinit var adapter: BillAdapter
    private lateinit var binding: FragmentBillsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillsBinding.inflate(inflater, container, false)

        binding.rvBills.setHasFixedSize(true)
        binding.rvBills.layoutManager = LinearLayoutManager(context)
        binding.rvBills.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (activity != null) {
            dbWorkerThread = DbWorkerThread("dbWorkerThread")
            dbWorkerThread.start()

            if (context != null) {
                db = AppDatabase.getInstance(context!!)

                adapter = BillAdapter(context!!) { bill ->
                    val intent = Intent(context, BillDetailActivity::class.java)
                    intent.putExtra(BILL_ID, bill)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        getBillData()
    }

    private fun getBillData() {
        bills.clear()
        val task = Runnable {
            val billData = db?.billDao()?.getBills()
            handler.post {
                billData?.let { bills.addAll(it) }
                if (bills.isEmpty()) {
                    binding.tvNoData.visibility = View.VISIBLE
                    binding.rvBills.visibility = View.GONE
                } else {
                    binding.tvNoData.visibility = View.GONE
                    binding.rvBills.visibility = View.VISIBLE
                }
                adapter.setBills(bills)
                adapter.notifyDataSetChanged()
            }
        }
        dbWorkerThread.postTask(task)
    }

    companion object {
        fun newInstance(): Fragment {
            return BillsFragment()
        }
    }
}
