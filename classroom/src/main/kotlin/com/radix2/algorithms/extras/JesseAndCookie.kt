package com.radix2.algorithms.extras

import java.util.*

fun cookies(k: Int, cookies: Array<Int>): Int {
    val pq = PriorityQueue<Int>()

    var count = 0

    for (cookie in cookies) {
        pq.add(cookie)
    }

    while (pq.peek() < k && pq.size >= 2) {
        val c1 = pq.poll()

        val c2 = pq.poll()

        pq.add(c1 + 2 * c2)

        count++
    }

    return if (pq.peek() >= k) count else -1
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val nk = scan.nextLine().split(" ")

    val n = nk[0].trim().toInt()

    val k = nk[1].trim().toInt()

    val A = scan.nextLine().split(" ").map { it.trim().toInt() }.toTypedArray()

    val result = cookies(k, A)

    println(result)
}