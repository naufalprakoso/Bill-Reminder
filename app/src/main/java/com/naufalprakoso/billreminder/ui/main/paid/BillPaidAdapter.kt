package com.naufalprakoso.billreminder.ui.main.paid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufalprakoso.billreminder.R
import com.naufalprakoso.billreminder.database.entity.Bill
import kotlinx.android.synthetic.main.item_bill_unpaid.view.*

class BillPaidAdapter(
    private val showDetail: (Bill) -> Unit
) : RecyclerView.Adapter<BillPaidAdapter.ViewHolder>() {

    private val bills = arrayListOf<Bill>()

    fun setBills(bills: List<Bill>) {
        this.bills.clear()
        this.bills.addAll(bills)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_bill_paid, parent, false)
        )

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(bills[position], showDetail)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        fun bindItem(
            bill: Bill,
            showDetail: (Bill) -> Unit
        ) {
            itemView.tv_title.text = bill.title
            itemView.tv_amount.text = itemView.context.getString(R.string.bill_amount, bill.amount)
            itemView.tv_content.text = bill.content

            itemView.setOnClickListener {
                showDetail(bill)
            }
        }
    }

}