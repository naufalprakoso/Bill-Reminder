package com.naufalprakoso.billreminder.utils

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.naufalprakoso.billreminder.database.entity.Bill

class DiffUtilCallback(
    private val oldList: List<Bill>,
    private val newList: List<Bill>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (_, value, name) = oldList[oldItemPosition]
        val (_, value1, name1) = newList[newItemPosition]

        return name == name1 && value == value1
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}