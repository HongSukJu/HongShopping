package com.example.hongshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : AppCompatActivity() {
    private val cartItemList: ArrayList<CartItem> = MainActivity.cartItemList
    private val cartItemListViewAdapter = CartItemListViewAdapter(cartItemList, ::onClickDeleteCartItemButton, ::onClickCheckBox)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar_cart)
        toolbar.title = "Cart"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setCartItemList()

        val cartBuyBtn: Button = findViewById(R.id.btn_buy_cart)
        cartBuyBtn.setOnClickListener {
            onClickCartBuyButton()
        }
    }

    private fun setCartItemList() {
        val cartItemListView: RecyclerView = findViewById(R.id.list_item_cart)

        cartItemListView.setHasFixedSize(true)
        cartItemListView.adapter = cartItemListViewAdapter
        cartItemListView.layoutManager = LinearLayoutManager(this)

        setQuantityAndSumPrice()
    }

    private fun onClickCheckBox(item: CartItem) {
        item.checked = !item.checked
        setQuantityAndSumPrice()
    }

    private fun onClickDeleteCartItemButton(pos: Int) {
        cartItemList.removeAt(pos)
        setQuantityAndSumPrice()
        cartItemListViewAdapter.notifyDataSetChanged()
    }

    private fun onClickCartBuyButton() {
        if (cartItemList.none { it.checked }) {
            Toast.makeText(this, "선택된 상품이 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        MainActivity.buyItemList.clear()
        cartItemList.filter { it.checked }.forEach {
            MainActivity.buyItemList.add(BuyItem(it.name, it.price, it.logo, it.quantity))
        }
        val intent: Intent = Intent(this, BuyActivity::class.java)
        startActivity(intent)
    }

    private fun setQuantityAndSumPrice() {
        val cartItemListQuantity: TextView = findViewById(R.id.quantity_sum_cart)
        val cartItemListSumPrice: TextView = findViewById(R.id.price_sum_cart)

        cartItemListQuantity.text = String.format("%s%s", cartItemList.filter { it.checked }.size, "개")
        var sumPrice = 0
        cartItemList.filter { it.checked }.forEach {
            sumPrice += it.price * it.quantity
        }
        cartItemListSumPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(sumPrice), "원")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}