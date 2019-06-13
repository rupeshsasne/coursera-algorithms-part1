package com.radix2.algorithms.week3

object TraditionalMergeSort {
    fun sort(array: IntArray) {
        val aux = IntArray(array.size)
        sort(array, aux, 0, array.size - 1)
    }

    fun sort(array: IntArray, aux: IntArray, lo: Int, hi: Int) {
        if (lo >= hi) return

        val mid = lo + (hi - lo) / 2

        sort(array, aux, lo, mid)
        sort(array, aux, mid + 1, hi)
        merge(array, aux, lo, mid, hi)
    }

    fun merge(array: IntArray, aux: IntArray, lo: Int, mid: Int, hi: Int) {
        System.arraycopy(array, lo, aux, lo, (hi - lo) + 1)

        var i = lo
        var j = mid + 1

        for (k in lo..hi) {
            when {
                i > mid -> array[k] = aux[j++]
                j > hi -> array[k] = aux[i++]
                aux[i] > aux[j] -> {
                    array[k] = aux[j++]
                }
                else -> array[k] = aux[i++]
            }
        }
    }
}

fun main(args: Array<String>) {
    val array = intArrayOf(6, 2, 1, 0, 13, 89, 72, 4, 22, 28)
    TraditionalMergeSort.sort(array)
    println(array.joinToString())
}