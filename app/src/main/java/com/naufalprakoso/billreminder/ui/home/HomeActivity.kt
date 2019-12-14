package com.naufalprakoso.billreminder.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.ui.bill.add.AddBillActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread
    private val handler = Handler()

    private lateinit var adapter: BillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()

        db = AppDatabase.getInstance(this)

        adapter = BillAdapter()

        rv_bills.setHasFixedSize(true)
        rv_bills.layoutManager = LinearLayoutManager(this)
        rv_bills.adapter = adapter

        fab.setOnClickListener {
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        getBillData()
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        dbWorkerThread.quit()
        super.onDestroy()
    }

    private fun getBillData() {
        val task = Runnable {
            val bills = db?.billDao()?.getUnpaidBill()
            handler.post {
                if (bills == null || bills.isEmpty()) {
                    Toast.makeText(this, "No data ", Toast.LENGTH_SHORT).show()
                } else {
                    adapter.setBills(bills)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        dbWorkerThread.postTask(task)
    }
}