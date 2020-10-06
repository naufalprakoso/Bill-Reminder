package com.naufalprakoso.billreminder.ui.bill.detail

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.ActivityBillDetailBinding
import com.naufalprakoso.billreminder.utils.BILL_ID
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale

@AndroidEntryPoint
class BillDetailActivity : AppCompatActivity() {

    private val billDetailViewModel: BillDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityBillDetailBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
        billDetailViewModel.updateBill(bill)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
