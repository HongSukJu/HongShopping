package com.example.hongshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    // TODO: 셀링 창 없애는법이 있어야함.
    private val cartList: ArrayList<CartItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar_main)
        toolbar.title = "HongShopping"
        setSupportActionBar(toolbar)

        setItemList()
    }

    private fun setItemList() {
        val itemListView: RecyclerView = findViewById(R.id.list_item)
        val itemList: ArrayList<Item> = ArrayList(mutableListOf(
            Item("Nintendo Switch", 360000, R.drawable.img_switch),
            Item("Xbox Series X", 598000, R.drawable.img_xbox_series_x),
            Item("Xbox Series S", 398000, R.drawable.img_xbox_series_s),
            Item("PlayStation5", 498000, R.drawable.img_playstation)
        ))
        val itemListViewAdapter = ItemListViewAdapter(itemList, ::onClickItem)

        itemListView.setHasFixedSize(true)
        itemListView.adapter = itemListViewAdapter
        itemListView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun onClickItem(item: Item) {
        val sellingLayout: RelativeLayout = findViewById(R.id.layout_selling)

        val selectedImage: ImageView = findViewById(R.id.image_selected)
        val selectedName: TextView = findViewById(R.id.name_selected)
        val selectedPrice: TextView = findViewById(R.id.price_selected)
        val selectedQuantity: TextView = findViewById(R.id.quantity_selected)

        val plusBtn: Button = findViewById(R.id.btn_plus)
        val minusBtn: Button = findViewById(R.id.btn_minus)
        val buyBtn: Button = findViewById(R.id.btn_buy)
        val cartBtn: Button = findViewById(R.id.btn_cart)

        var quantity = 1
        var priceComma = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price) + "원"
        selectedQuantity.text = quantity.toString()

        plusBtn.setOnClickListener {
            quantity++
            selectedQuantity.text = quantity.toString()
            priceComma = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price * quantity) + "원"
            selectedPrice.text = priceComma
        }
        minusBtn.setOnClickListener {
            if (quantity == 1) return@setOnClickListener
            quantity--
            selectedQuantity.text = quantity.toString()
            priceComma = NumberFormat.getNumberInstance(Locale.KOREA).format(item.price * quantity) + "원"
            selectedPrice.text = priceComma
        }
        cartBtn.setOnClickListener {
            if (cartList.any { ele -> ele.name == item.name }) {
                // TODO: 이미 있는데 어떻게 할건지?
                return@setOnClickListener
            }

            cartList.add(
                    CartItem(item.name, item.price, item.logo, quantity)
            )
            val snackbar: Snackbar = Snackbar.make(
                    findViewById(R.id.layout_main),
                    "장바구니에 추가되었습니다.",
                    Snackbar.LENGTH_LONG
            )
            sellingLayout.visibility = View.INVISIBLE
            snackbar.show()
        }
        buyBtn.setOnClickListener {
            // TODO: 장바구니에 물건이 있으면, 무시하고 이것만 구입할건지, 아니면 추가하고 구매할건지.
        }

        selectedImage.setImageResource(item.logo)
        selectedName.text = item.name
        selectedPrice.text = priceComma

        sellingLayout.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_heart -> {
            // TODO: 후에 찜 기능 추가? 할지말지모름
            false
        }
        R.id.menu_cart -> {
            val intent: Intent = Intent(this, CartActivity::class.java)
            intent.putExtra("cartList", cartList)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}