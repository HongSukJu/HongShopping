package com.example.hongshopping

import java.io.Serializable

open class Item(val name: String?, val price: Int, val logo: Int) : Serializable {}