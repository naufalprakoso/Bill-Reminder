package com.naufalprakoso.billreminder.ui.bill.add

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.AppDatabase
import com.naufalprakoso.billreminder.database.DbWorkerThread
import com.naufalprakoso.billreminder.database.entity.Bill
import kotlinx.android.synthetic.main.activity_add_bill.*

class AddBillActivity : AppCompatActivity() {

    private var db: AppDatabase? = null

    private lateinit var dbWorkerThread: DbWorkerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bill)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dbWorkerThread = DbWorkerThread("dbWorkerThread")
        dbWorkerThread.start()

        db = AppDatabase.getInstance(this)

        fab.setOnClickListener {
            val title = edt_title.text.toString()
            val content = edt_content.text.toString()
            val amount = edt_amount.text.toString()

            when {
                title.isEmpty() -> edt_title.error = getString(R.string.validation_filled)
                content.isEmpty() -> edt_content.error = getString(R.string.validation_filled)
                amount.isEmpty() -> edt_amount.error = getString(R.string.validation_filled)
                else -> {
                    val bill = Bill(
                        0,
                        title,
                        content,
                        amount.toInt(),
                        false
                    )

                    insertBill(bill)

                    Toast.makeText(this, "Add Bill successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }


    private fun insertBill(bill: Bill) {
        val task = Runnable { db?.billDao()?.insert(bill) }
        dbWorkerThread.postTask(task)
    }

    override fun onDestroy() {
        AppDatabase.destroyInstance()
        dbWorkerThread.quit()
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
