package com.naufalprakoso.billreminder.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naufalprakoso.billreminder.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fab.setOnClickListener {
//            val intent = Intent(this)
        }
    }
}