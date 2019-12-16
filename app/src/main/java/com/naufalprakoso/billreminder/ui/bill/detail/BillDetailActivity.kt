package com.naufalprakoso.billreminder.ui.bill.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.utils.Const
import kotlinx.android.synthetic.main.activity_bill_detail.*

class BillDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bill = intent.getParcelableExtra<Bill>(Const.BILL_ID)

        tv_title.text = bill?.title
        tv_content.text = bill?.content
        tv_amount.text = getString(R.string.bill_amount, bill?.amount)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
