package com.example.hongshopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class BuyItemListViewAdapter(private val items: ArrayList<BuyItem>) :
        RecyclerView.Adapter<BuyItemListViewAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.logo_item_buy)
        val name: TextView = view.findViewById(R.id.name_item_buy)
        val totalPrice: TextView = view.findViewById(R.id.price_total_item_buy)
        val priceQuantity: TextView = view.findViewById(R.id.price_quantity_item_buy)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_buy, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.logo.setImageResource(item.logo)
        holder.name.text = item.name
        holder.priceQuantity.text = String.format(
                "%s%s %s",
                NumberFormat.getNumberInstance(Locale.KOREA).format(item.price),
                "원 X",
                item.quantity
        )
        holder.totalPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(item.price * item.quantity), "원")
    }
}