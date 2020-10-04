package com.naufalprakoso.billreminder.ui.bill.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.ActivityBillDetailBinding
import com.naufalprakoso.billreminder.utils.BILL_ID
import java.text.NumberFormat
import java.util.*

class BillDetailActivity : AppCompatActivity() {

    private var db: AppDatabase? = null
    private lateinit var dbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBillDetailBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()

        db = AppDatabase.buildDatabase(this)

        val bill = intent.getParcelableExtra<Bill>(BILL_ID)

        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        bill?.let {
            val amount = formatRupiah.format(bill.amount.toDouble())

            binding.tvTitle.text = bill.title
            binding.tvContent.text = bill.content
            binding.tvAmount.text = amount
        }

        bill?.let {
            if (bill.paid == "true") {
                binding.fab.visibility = View.GONE
            }

            binding.fab.setOnClickListener {
                val newBill = bill.copy(paid = "true")
                updateBill(newBill)
                binding.fab.visibility = View.GONE
            }
        }

        setContentView(binding.root)
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
