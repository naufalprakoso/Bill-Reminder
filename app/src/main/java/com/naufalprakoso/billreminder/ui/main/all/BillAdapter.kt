package com.naufalprakoso.billreminder.ui.main.all

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.entity.Bill
import kotlinx.android.synthetic.main.item_bill_unpaid.view.*
import java.text.NumberFormat
import java.util.*

class BillAdapter(
    private val showDetail: (Bill) -> Unit
) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    private val bills = arrayListOf<Bill>()

    fun setBills(bills: List<Bill>) {
        this.bills.clear()
        this.bills.addAll(bills)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_bill_unpaid,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(bills[position], showDetail)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        inline fun bindItem(
            bill: Bill,
            crossinline showDetail: (Bill) -> Unit
        ) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            val amount = formatRupiah.format(bill.amount.toDouble())

            itemView.tv_title.text = bill.title
            itemView.tv_amount.text = amount
            itemView.tv_content.text = bill.content
            itemView.cb_paid.isChecked = bill.paid.toBoolean()
            itemView.cb_paid.isEnabled = false

            itemView.setOnClickListener {
                showDetail(bill)
            }
        }
    }

}