package com.example.hongshopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CartActivity : AppCompatActivity() {
    // TODO: 구매기능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val toolbar: Toolbar = findViewById(R.id.toolbar_cart)
        toolbar.title = "Cart"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setCartItemList()
    }

    private fun setCartItemList() {
        val cartItemListView: RecyclerView = findViewById(R.id.list_item_cart)
        val cartItemList: ArrayList<CartItem> = intent.getSerializableExtra("cartList") as ArrayList<CartItem>
        val cartItemListViewAdapter = CartItemListViewAdapter(cartItemList, ::onClickCartItem)

        cartItemListView.setHasFixedSize(true)
        cartItemListView.adapter = cartItemListViewAdapter
        cartItemListView.layoutManager = LinearLayoutManager(this)
    }

    private fun onClickCartItem(item: CartItem) {
        // TODO: 삭제기능 넣어야될듯
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


}