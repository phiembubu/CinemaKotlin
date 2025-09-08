package com.example.cinema.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.adapter.BookingHistoryAdapter.BookingHistoryViewHolder
import com.example.cinema.constant.ConstantKey
import com.example.cinema.databinding.ItemBookingHistoryBinding
import com.example.cinema.listener.IOnSingleClickListener
import com.example.cinema.model.BookingHistory
import com.example.cinema.util.DateTimeUtils

class BookingHistoryAdapter(private var mContext: Context?, private val mIsAdmin: Boolean,
                            private val mListBookingHistory: List<BookingHistory>?,
                            private val iClickQRListener: IClickQRListener?,
                            private val iClickConfirmListener: IClickConfirmListener?) : RecyclerView.Adapter<BookingHistoryViewHolder>() {
    interface IClickQRListener {
        fun onClickOpenQrCode(id: String?)
    }

    interface IClickConfirmListener {
        fun onClickConfirmBooking(id: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingHistoryViewHolder {
        val itemBookingHistoryBinding = ItemBookingHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookingHistoryViewHolder(itemBookingHistoryBinding)
    }

    override fun onBindViewHolder(holder: BookingHistoryViewHolder, position: Int) {
        val bookingHistory = mListBookingHistory!![position]
        val isExpire = DateTimeUtils.convertDateToTimeStamp(bookingHistory.date) < DateTimeUtils.getLongCurrentTimeStamp()
        if (isExpire || bookingHistory.isUsed) {
            holder.mItemBookingHistoryBinding.layoutItem.setBackgroundColor(mContext!!.resources.getColor(R.color.black_overlay))
        } else {
            holder.mItemBookingHistoryBinding.layoutItem.setBackgroundColor(mContext!!.resources.getColor(R.color.white))
        }
        holder.mItemBookingHistoryBinding.tvId.text = bookingHistory.id.toString()
        holder.mItemBookingHistoryBinding.tvNameMovie.text = bookingHistory.name
        holder.mItemBookingHistoryBinding.tvDateMovie.text = bookingHistory.date
        holder.mItemBookingHistoryBinding.tvRoomMovie.text = bookingHistory.room
        holder.mItemBookingHistoryBinding.tvTimeMovie.text = bookingHistory.time
        holder.mItemBookingHistoryBinding.tvCountBooking.text = bookingHistory.count
        holder.mItemBookingHistoryBinding.tvCountSeat.text = bookingHistory.seats
        holder.mItemBookingHistoryBinding.tvFoodDrink.text = bookingHistory.foods
        holder.mItemBookingHistoryBinding.tvPaymentMethod.text = bookingHistory.payment
        val strTotal = bookingHistory.total.toString() + ConstantKey.UNIT_CURRENCY
        holder.mItemBookingHistoryBinding.tvTotalAmount.text = strTotal
        holder.mItemBookingHistoryBinding.tvDateCreate.text = DateTimeUtils.convertTimeStampToDate(bookingHistory.id.toString())
        if (mIsAdmin) {
            holder.mItemBookingHistoryBinding.imgQr.visibility = View.GONE
            holder.mItemBookingHistoryBinding.layoutEmail.visibility = View.VISIBLE
            holder.mItemBookingHistoryBinding.tvEmail.text = bookingHistory.user
            if (isExpire || bookingHistory.isUsed) {
                holder.mItemBookingHistoryBinding.layoutConfirm.visibility = View.GONE
            } else {
                holder.mItemBookingHistoryBinding.layoutConfirm.visibility = View.VISIBLE
                holder.mItemBookingHistoryBinding.chbConfirm.setOnClickListener(object : IOnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        iClickConfirmListener!!.onClickConfirmBooking(bookingHistory.id.toString())
                    }
                })
            }
        } else {
            holder.mItemBookingHistoryBinding.layoutConfirm.visibility = View.GONE
            holder.mItemBookingHistoryBinding.layoutEmail.visibility = View.GONE
            holder.mItemBookingHistoryBinding.imgQr.visibility = View.VISIBLE
            if (isExpire || bookingHistory.isUsed) {
                holder.mItemBookingHistoryBinding.imgQr.setOnClickListener(null)
            } else {
                holder.mItemBookingHistoryBinding.imgQr.setOnClickListener(object : IOnSingleClickListener() {
                    override fun onSingleClick(v: View?) {
                        iClickQRListener!!.onClickOpenQrCode(bookingHistory.id.toString())
                    }
                })
            }
        }
    }

    fun release() {
        mContext = null
    }

    override fun getItemCount(): Int {
        return mListBookingHistory?.size ?: 0
    }

    class BookingHistoryViewHolder(val mItemBookingHistoryBinding: ItemBookingHistoryBinding) : RecyclerView.ViewHolder(mItemBookingHistoryBinding.root)
}