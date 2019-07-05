package com.radix2.algorithms.extras

import java.util.PriorityQueue

fun runningMedian(a: Array<Int>): Array<Double> {
    val minPQ = PriorityQueue<Int> { o1, o2 -> o2.compareTo(o1) }
    val maxPQ = PriorityQueue<Int>()

    val medians = Array(a.size) { 0.0 }

    for (i in 0..a.lastIndex) {
        add(a[i], minPQ, maxPQ)
        rebalance(minPQ, maxPQ)
        medians[i] = getMedian(minPQ, maxPQ)
    }

    return medians
}

fun getMedian(minPQ: PriorityQueue<Int>, maxPQ: PriorityQueue<Int>): Double {
    val maxHeap = if (minPQ.size > maxPQ.size) minPQ else maxPQ
    val minHeap = if (minPQ.size > maxPQ.size) maxPQ else minPQ

    return if (maxHeap.size == minHeap.size) {
        (maxHeap.peek() + minHeap.peek()).toDouble() / 2
    } else {
        maxHeap.peek().toDouble()
    }
}

fun rebalance(minPQ: PriorityQueue<Int>, maxPQ: PriorityQueue<Int>) {
    val biggerHeap = if (minPQ.size > maxPQ.size) minPQ else maxPQ
    val smallerHeap = if (minPQ.size > maxPQ.size) maxPQ else minPQ

    if (biggerHeap.size - smallerHeap.size >= 2) {
        smallerHeap.add(biggerHeap.poll())
    }
}

fun add(num: Int, minPQ: PriorityQueue<Int>, maxPQ: PriorityQueue<Int>) {

    if (minPQ.isEmpty() || num < minPQ.peek()) {
        minPQ.add(num)
    } else {
        maxPQ.add(num)
    }
}

fun main(args: Array<String>) {
    val reader = System.`in`.bufferedReader()

    val aCount = reader.readLine().trim().toInt()

    val a = Array(aCount) { 0 }

    for (aItr in 0 until aCount) {
        val aItem = reader.readLine().trim().toInt()
        a[aItr] = aItem
    }

    reader.close()

    val result = runningMedian(a)

    println(result.joinToString("\n"))
}
