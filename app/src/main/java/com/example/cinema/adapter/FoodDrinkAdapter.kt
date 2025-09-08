package com.example.cinema.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.adapter.FoodDrinkAdapter.FoodDrinkViewHolder
import com.example.cinema.constant.ConstantKey
import com.example.cinema.databinding.ItemFoodDrinkBinding
import com.example.cinema.model.Food
import com.example.cinema.util.StringUtil

class FoodDrinkAdapter(private val mListFood: List<Food>?,
                       private val iManagerFoodDrinkListener: IManagerFoodDrinkListener) : RecyclerView.Adapter<FoodDrinkViewHolder>() {
    interface IManagerFoodDrinkListener {
        fun selectCount(food: Food, count: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodDrinkViewHolder {
        val itemFoodDrinkBinding = ItemFoodDrinkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodDrinkViewHolder(itemFoodDrinkBinding)
    }

    override fun onBindViewHolder(holder: FoodDrinkViewHolder, position: Int) {
        val food = mListFood!![position]
        holder.mItemFoodDrinkBinding.tvNameFood.text = food.name
        val strPrice = food.price.toString() + ConstantKey.UNIT_CURRENCY
        holder.mItemFoodDrinkBinding.tvPriceFood.text = strPrice
        holder.mItemFoodDrinkBinding.tvStock.text = food.quantity.toString()
        holder.mItemFoodDrinkBinding.edtCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (!StringUtil.isEmpty(editable.toString().trim { it <= ' ' })) {
                    val count = editable.toString().toInt()
                    if (count > food.quantity) {
                        val context = holder.mItemFoodDrinkBinding.edtCount.context
                        Toast.makeText(context, context.getString(R.string.msg_count_invalid), Toast.LENGTH_SHORT).show()
                        holder.mItemFoodDrinkBinding.edtCount.setText("")
                    } else {
                        iManagerFoodDrinkListener.selectCount(food, count)
                    }
                } else {
                    iManagerFoodDrinkListener.selectCount(food, 0)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return mListFood?.size ?: 0
    }

    class FoodDrinkViewHolder(val mItemFoodDrinkBinding: ItemFoodDrinkBinding) : RecyclerView.ViewHolder(mItemFoodDrinkBinding.root)
}