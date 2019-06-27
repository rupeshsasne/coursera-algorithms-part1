package com.radix2.algorithms.week3

fun findKthSmallestElement(array: IntArray, k: Int): Int {
    var lo = 0
    var hi = array.size - 1

    while (lo < hi) {
        val pivotIndex = partition(array, lo, hi)

        when {
            pivotIndex < k -> lo = pivotIndex + 1
            pivotIndex > k -> hi = pivotIndex - 1
            else -> return array[k]
        }
    }

    return array[k]
}