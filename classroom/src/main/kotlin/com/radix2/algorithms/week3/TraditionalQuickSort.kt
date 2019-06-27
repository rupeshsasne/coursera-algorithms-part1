package com.radix2.algorithms.week3

import java.util.*

fun less(lhs: Int, rhs: Int) = lhs < rhs

fun exch(array: IntArray, i: Int, j: Int) {
    val temp = array[i]
    array[i] = array[j]
    array[j] = temp
}

fun shuffle(array: IntArray) {
    val rnd = Random()

    for (i in 1 until array.size) {
        val randomIndex = rnd.nextInt(i)
        exch(array, i, randomIndex)
    }
}

fun quickSort(array: IntArray) {
    shuffle(array)

    quickSort(array, 0, array.size - 1)
}

fun quickSort(array: IntArray, lo: Int, hi: Int) {
    if (hi <= lo) return

    val k = partition(array, lo, hi)

    quickSort(array, lo, k - 1)
    quickSort(array, k + 1, hi)
}

fun partition(array: IntArray, lo: Int, hi: Int): Int {
    val pivot = array[lo]
    var i = lo
    var j = hi + 1

    while (true) {

        while (less(array[++i], pivot))
            if (i == hi) break

        while (less(pivot, array[--j]))
            if (j == lo) break

        if (i >= j) break

        exch(array, i, j)
    }

    exch(array, lo, j)

    return j
}

// 0, 1, 2, 3, 4, 5, 6, 7, 9
fun main(args: Array<String>) {
    val array = intArrayOf(6, 10, 1, 3, 2, 6, 8, 9, 5, 15)

    quickSort(array)

    println(array.joinToString(", "))
}