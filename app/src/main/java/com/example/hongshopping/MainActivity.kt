package com.example.hongshopping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val itemList: ArrayList<Item> = ArrayList(mutableListOf(
            Item("Nintendo Switch", 360000, R.drawable.img_switch),
            Item("Xbox Series X", 598000, R.drawable.img_xbox_series_x),
            Item("Xbox Series S", 398000, R.drawable.img_xbox_series_s),
            Item("PlayStation5", 498000, R.drawable.img_playstation)
    ))
    private val itemListViewAdapter = ItemListViewAdapter(itemList, ::onClickItem)

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

        if (sellingLayout.visibility == View.VISIBLE && item.name == selectedName.text) {
            sellingLayout.visibility = View.INVISIBLE
            return
        }

        var quantity = 1
        selectedQuantity.text = quantity.toString()

        plusBtn.setOnClickListener {
            quantity++
            selectedQuantity.text = quantity.toString()
            selectedPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(item.price * quantity), "원")
        }
        minusBtn.setOnClickListener {
            if (quantity == 1) return@setOnClickListener
            quantity--
            selectedQuantity.text = quantity.toString()
            selectedPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(item.price * quantity), "원")
        }
        cartBtn.setOnClickListener {
            if (cartItemList.any { ele -> ele.name == item.name }) {
                Toast.makeText(this, "이미 장바구니에 상품이 있습니다.", Toast.LENGTH_SHORT).show()
                sellingLayout.visibility = View.INVISIBLE
                return@setOnClickListener
            }

            cartItemList.add(
                    CartItem(item.name, item.price, item.logo, quantity)
            )
            val snackbar: Snackbar = Snackbar.make(
                    findViewById(R.id.layout_main),
                    "장바구니에 추가되었습니다.",
                    Snackbar.LENGTH_SHORT
            )
            sellingLayout.visibility = View.INVISIBLE
            snackbar.show()
        }
        buyBtn.setOnClickListener {
            if (cartItemList.isEmpty()) {
                buyItemList.clear()
                buyItemList.add(BuyItem(item.name, item.price, item.logo, quantity))
                val intent: Intent = Intent(this, BuyActivity::class.java)
                startActivity(intent)

                sellingLayout.visibility = View.INVISIBLE

                return@setOnClickListener
            }

            val popupView: View = layoutInflater.inflate(R.layout.popup_buy, null)

            val ignoreBuyBtn: Button = popupView.findViewById(R.id.btn_yes_popup)
            val cancelBtn: Button = popupView.findViewById(R.id.btn_no_popup)

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setView(popupView)

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

            ignoreBuyBtn.setOnClickListener {
                buyItemList.clear()
                buyItemList.add(BuyItem(item.name, item.price, item.logo, quantity))
                val intent: Intent = Intent(this, BuyActivity::class.java)
                startActivity(intent)
            }
            cancelBtn.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        selectedImage.setImageResource(item.logo)
        selectedName.text = item.name
        selectedPrice.text = String.format("%s%s", NumberFormat.getNumberInstance(Locale.KOREA).format(item.price), "원")

        sellingLayout.visibility = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_cart -> {
            val intent: Intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    companion object {
        val cartItemList: ArrayList<CartItem> = ArrayList()
        val buyItemList: ArrayList<BuyItem> = ArrayList()
    }
}