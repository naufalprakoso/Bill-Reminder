package com.naufalprakoso.billreminder.ui.main.all

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.ui.bill.detail.BillDetailActivity
import com.naufalprakoso.billreminder.utils.Const
import kotlinx.android.synthetic.main.fragment_bills.view.*

class BillsFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return BillsFragment()
        }
    }

    private val bills = arrayListOf<Bill>()

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val handler = Handler()

    private lateinit var adapter: BillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()

        db = context?.let { AppDatabase.getInstance(it) }

        adapter = BillAdapter { bill ->
            val intent = Intent(context, BillDetailActivity::class.java)
            intent.putExtra(Const.BILL_ID, bill)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bills, container, false)

        view.rv_bills?.setHasFixedSize(true)
        view.rv_bills?.layoutManager = LinearLayoutManager(context)
        view.rv_bills?.adapter = adapter

        return view
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
                    view?.tv_no_data?.visibility = View.VISIBLE
                    view?.rv_bills?.visibility = View.GONE
                } else {
                    view?.tv_no_data?.visibility = View.GONE
                    view?.rv_bills?.visibility = View.VISIBLE
                }
                adapter.setBills(bills)
                adapter.notifyDataSetChanged()
            }
        }
        dbWorkerThread.postTask(task)
    }
}
