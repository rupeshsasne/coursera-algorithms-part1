package com.radix2.algorithms.extras

import java.util.*

fun minimumAverage(orders: Sequence<Order>): Long {
    val arrivalTimes = PriorityQueue<Order> { o1, o2 -> o1.arrivalTime.compareTo(o2.arrivalTime) }
    val burstTimes = PriorityQueue<Order> { o1, o2 -> o1.burstTime.compareTo(o2.burstTime) }

    for (order in orders) {
        arrivalTimes.add(order)
    }

    var currentTime = 0L
    var totalWaitTime = 0L
    var numberOfOrdersServed = 0L

    fun processOrder(order: Order) {
        totalWaitTime += (currentTime - order.arrivalTime + order.burstTime)
        currentTime += order.burstTime
        numberOfOrdersServed++
    }

    fun enqueueOrders() {
        while (!arrivalTimes.isEmpty() && arrivalTimes.peek().arrivalTime <= currentTime) {
            burstTimes.add(arrivalTimes.poll())
        }
    }

    while (!arrivalTimes.isEmpty()) {
        var order = arrivalTimes.poll()

        currentTime = if (burstTimes.isEmpty()) order.arrivalTime else currentTime + order.arrivalTime

        processOrder(order)
        enqueueOrders()

        while (!burstTimes.isEmpty()) {
            order = burstTimes.poll()
            processOrder(order)
            enqueueOrders()
        }
    }

    return totalWaitTime / numberOfOrdersServed
}

data class Order(val arrivalTime: Long, val burstTime: Long)

fun main(args: Array<String>) {
    val reader = System.`in`.bufferedReader()

    val n = reader.readLine().trim().toInt()

    val orders = Array<Order?>(n) { null }

    for (ordersRowItr in 0 until n) {
        val (arrivalTime, burstTime) = reader.readLine().split(" ").map { it.trim().toLong() }

        orders[ordersRowItr] = Order(arrivalTime, burstTime)
    }

    val result = minimumAverage(orders.asSequence().filterNotNull())

    println(result)
}
