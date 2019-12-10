package com.naufalprakoso.billreminder.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.ui.bill.add.AddBillActivity
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_bills.setHasFixedSize(true)
        rv_bills.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, AddBillActivity::class.java)
            startActivity(intent)
        }
    }
}