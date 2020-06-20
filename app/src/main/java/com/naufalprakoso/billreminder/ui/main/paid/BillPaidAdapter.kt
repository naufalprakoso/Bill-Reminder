package com.naufalprakoso.billreminder.ui.main.paid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufalprakoso.billreminder.database.entity.Bill
import com.naufalprakoso.billreminder.databinding.ItemBillPaidBinding
import java.text.NumberFormat
import java.util.Locale

class BillPaidAdapter(
    private val context: Context,
    private val showDetail: (Bill) -> Unit
) : RecyclerView.Adapter<BillPaidAdapter.ViewHolder>() {

    private val bills = arrayListOf<Bill>()
    private lateinit var binding: ItemBillPaidBinding

    fun setBills(bills: List<Bill>) {
        this.bills.clear()
        this.bills.addAll(bills)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        binding = ItemBillPaidBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int = bills.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(binding, bills[position], showDetail)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        inline fun bindItem(
            binding: ItemBillPaidBinding,
            bill: Bill,
            crossinline showDetail: (Bill) -> Unit
        ) {
            val localeID = Locale("in", "ID")
            val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
            val amount = formatRupiah.format(bill.amount.toDouble())

            binding.tvTitle.text = bill.title
            binding.tvAmount.text = amount
            binding.tvContent.text = bill.content

            binding.cvContainer.setOnClickListener {
                showDetail(bill)
            }
        }
    }

}