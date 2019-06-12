package com.radix2.algorithms.extras

import java.util.*

fun maximumToys(prices: Array<Int>, k: Int): Int {
    prices.sort()

    var budget = k

    var count = 0

    for (price in prices) {
        budget -= price

        if (budget < 0)
            break

        count++
    }

    return count
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nk = scan.nextLine().split(" ")

    val n = nk[0].trim().toInt()

    val k = nk[1].trim().toInt()

    val prices = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    val result = maximumToys(prices, k)

    println(result)
}