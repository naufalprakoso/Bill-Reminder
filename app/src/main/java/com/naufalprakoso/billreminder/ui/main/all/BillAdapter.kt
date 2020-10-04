package com.naufalprakoso.billreminder.ui.main.all

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.ItemBillUnpaidBinding
import java.text.NumberFormat
import java.util.*

class BillAdapter(
    private val context: Context,
    private val showDetail: (Bill) -> Unit
) : RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    private val bills = arrayListOf<Bill>()
    private lateinit var binding: ItemBillUnpaidBinding

    fun setBills(bills: List<Bill>) {
        if (bills.isNullOrEmpty()) this.bills.clear()
        else this.bills.addAll(bills)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        binding = ItemBillUnpaidBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(binding, bills[position], showDetail)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        inline fun bindItem(
            binding: ItemBillUnpaidBinding,
            bill: Bill,
            crossinline showDetail: (Bill) -> Unit
        ) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            val amount = formatRupiah.format(bill.amount.toDouble())

            binding.tvTitle.text = bill.title
            binding.tvAmount.text = amount
            binding.tvContent.text = bill.content
            binding.cbPaid.isChecked = bill.paid.toBoolean()
            binding.cbPaid.isEnabled = false

            binding.cvContainer.setOnClickListener {
                showDetail(bill)
            }
        }
    }

}