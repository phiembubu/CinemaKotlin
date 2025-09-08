package com.example.cinema.adapter.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.adapter.admin.AdminRevenueAdapter.AdminRevenueViewHolder
import com.example.cinema.constant.ConstantKey
import com.example.cinema.databinding.ItemRevenueBinding
import com.example.cinema.model.Revenue

class AdminRevenueAdapter(private val mListRevenue: List<Revenue>?) : RecyclerView.Adapter<AdminRevenueViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminRevenueViewHolder {
        val itemRevenueBinding = ItemRevenueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminRevenueViewHolder(itemRevenueBinding)
    }

    override fun onBindViewHolder(holder: AdminRevenueViewHolder, position: Int) {
        val revenue = mListRevenue!![position]
        holder.mItemRevenueBinding.tvStt.text = (position + 1).toString()
        holder.mItemRevenueBinding.tvMovieName.text = revenue.movieName
        holder.mItemRevenueBinding.tvQuantity.text = revenue.quantity.toString()
        val total = revenue.totalPrice.toString() + ConstantKey.UNIT_CURRENCY
        holder.mItemRevenueBinding.tvTotalPrice.text = total
    }

    override fun getItemCount(): Int {
        return mListRevenue?.size ?: 0
    }

    class AdminRevenueViewHolder(val mItemRevenueBinding: ItemRevenueBinding) : RecyclerView.ViewHolder(mItemRevenueBinding.root)
}