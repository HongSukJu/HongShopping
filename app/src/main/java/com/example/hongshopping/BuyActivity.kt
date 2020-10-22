package com.example.hongshopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class BuyActivity : AppCompatActivity() {
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

    fun onClickBuyButton(view: View) {
        val editPhone: EditText = findViewById(R.id.edit_phone)
        val editAddress: EditText = findViewById(R.id.edit_address)

        val phone: String = editPhone.text.toString()
        val address: String = editAddress.text.toString()

        if (phone == "" || address == "") {
            Toast.makeText(this, "전화번호나 주소를 전부 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val direct: Boolean = intent.getBooleanExtra("direct", false)

        if (!direct) {
            MainActivity.cartItemList.clear()
        }

        writeOrderInDateBase(phone, address)
        setResult(RESULT_OK)
        finish()
    }

    private fun writeOrderInDateBase(phone: String, address: String) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("order")

        val items: ArrayList<Map<String, Any?>> = ArrayList()
        buyItemList.forEach {
            items.add(mapOf(
                "name" to it.name,
                "price" to it.price,
                "quantity" to it.quantity
            ))
        }
        val data: Map<String, Any?> = mapOf(
            "phone" to phone,
            "address" to address,
            "items" to items
        )
        myRef.setValue(data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
