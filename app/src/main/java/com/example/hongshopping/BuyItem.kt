package com.example.hongshopping

import java.io.Serializable

class BuyItem(name: String?, price: Int, logo: Int, val quantity: Int) : Item(name, price, logo) {}