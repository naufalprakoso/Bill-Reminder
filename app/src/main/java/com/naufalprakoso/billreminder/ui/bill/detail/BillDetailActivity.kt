package com.naufalprakoso.billreminder.ui.bill.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.utils.Const
import kotlinx.android.synthetic.main.activity_bill_detail.*
import java.text.NumberFormat
import java.util.*

class BillDetailActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()

        db = AppDatabase.getInstance(this)

        val bill = intent.getParcelableExtra<Bill>(Const.BILL_ID)

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        bill?.let {
            val amount = formatRupiah.format(bill.amount.toDouble())

            tv_title.text = bill.title
            tv_content.text = bill.content
            tv_amount.text = amount
        }

        bill?.let {
            if (bill.paid == "true")
                fab.visibility = View.GONE

            fab.setOnClickListener {
                bill.paid = "true"
                updateBill(bill)
                fab.visibility = View.GONE
            }
        }
    }

    private fun updateBill(bill: Bill) {
        val task = Runnable { db?.billDao()?.update(bill) }
        dbWorkerThread.postTask(task)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
