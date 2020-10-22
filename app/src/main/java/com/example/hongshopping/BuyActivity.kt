package com.example.hongshopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class BuyActivity : AppCompatActivity() {
    // TODO: 결제버튼 완성하기
    private var buyItemList: ArrayList<BuyItem> = MainActivity.buyItemList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy)

        val toolbar: Toolbar = findViewById(R.id.toolbar_buying)
        toolbar.title = "Buying"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setBuyItemList()
    }

    private fun setBuyItemList() {
        val buyItemListView: RecyclerView = findViewById(R.id.list_item_buy)
        val buyItemListViewAdapter = BuyItemListViewAdapter(buyItemList)

        buyItemListView.setHasFixedSize(true)
        buyItemListView.adapter = buyItemListViewAdapter
        buyItemListView.layoutManager = LinearLayoutManager(this)

        setQuantityAndSumPrice()
    }

    private fun setQuantityAndSumPrice() {
        val buyItemListQuantity: TextView = findViewById(R.id.quantity_sum_buy)
        val buyItemListSumPrice: TextView = findViewById(R.id.price_sum_buy)

        buyItemListQuantity.text = String.format("%s%s", buyItemList.size, "개")
        var sumPrice = 0
        buyItemList.forEach {
            sumPrice += it.price * it.quantity
        }
        buyItemListSumPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(sumPrice), "원")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}