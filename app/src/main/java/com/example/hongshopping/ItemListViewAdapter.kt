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

class ItemListViewAdapter(private val items: ArrayList<Item>, val onClickItem: (Item) -> Unit) :
    RecyclerView.Adapter<ItemListViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.logo_item)
        val name: TextView = view.findViewById(R.id.name_item)
        val price: TextView = view.findViewById(R.id.price_item)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.logo.setImageResource(item.logo)
        holder.name.text = item.name
        holder.price.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(item.price), "원")

        holder.view.setOnClickListener {
            onClickItem(item)
        }
    }
}