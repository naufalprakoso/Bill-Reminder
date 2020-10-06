package com.naufalprakoso.billreminder.ui.bill.add

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.ActivityAddBillBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBillActivity : AppCompatActivity() {

    private val addBillViewModel: AddBillViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBillBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.fab.setOnClickListener {
            val title = binding.edtTitle.text.toString()
            val content = binding.edtContent.text.toString()
            val amount = binding.edtAmount.text.toString()

            when {
                title.isEmpty() -> binding.edtTitle.error = getString(R.string.validation_filled)
                content.isEmpty() -> binding.edtContent.error = getString(R.string.validation_filled)
                amount.isEmpty() -> binding.edtAmount.error = getString(R.string.validation_filled)
                else -> {
                    val bill = Bill(
                        0,
                        title,
                        content,
                        amount.toInt(),
                        "false"
                    )

                    insertBill(bill)

                    Toast.makeText(this, "Add Bill successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        setContentView(binding.root)
    }

    private fun insertBill(bill: Bill) {
        addBillViewModel.insertBill(bill)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
