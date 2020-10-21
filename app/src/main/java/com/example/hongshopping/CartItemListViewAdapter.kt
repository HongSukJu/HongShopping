package com.example.hongshopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartItemListViewAdapter(private val items: ArrayList<CartItem>,
                              private val onClickDeleteCartItemButton: (pos: Int) -> Unit,
                              private val onClickCheckBox: (item: CartItem) -> Unit) :
        RecyclerView.Adapter<CartItemListViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.logo_item_cart)
        val name: TextView = view.findViewById(R.id.name_item_cart)
        val priceAndQuantity: TextView = view.findViewById(R.id.price_quantity_item_cart)
        val totalPrice: TextView = view.findViewById(R.id.price_total_item_cart)
        val checkBox: CheckBox = view.findViewById(R.id.checkbox_item_cart)
        val deleteCartItemButton: Button = view.findViewById(R.id.btn_delete_item_cart)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_cart, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.logo.setImageResource(item.logo)
        holder.name.text = item.name
        holder.priceAndQuantity.text = String.format(
                "%s%s %s",
                NumberFormat.getNumberInstance(Locale.KOREA).format(item.price),
                "원 X",
                item.quantity
        )
        holder.totalPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(item.price * item.quantity), "원")
        holder.checkBox.isChecked = item.checked
        holder.checkBox.setOnClickListener {
            onClickCheckBox(item)
        }
        holder.deleteCartItemButton.setOnClickListener {
            onClickDeleteCartItemButton(position)
        }
    }
}