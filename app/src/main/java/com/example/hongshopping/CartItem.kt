package com.example.hongshopping

class CartItem(name: String?, price: Int, logo: Int, val quantity: Int, var checked: Boolean = true) : Item(name, price, logo) {}