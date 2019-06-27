package com.radix2.algorithms.week3

class ThreeWayQuickSort<T: Comparable<T>> {

    fun sort(array: Array<Comparable<T>>, lo: Int, hi: Int) {
        if (hi <= lo) return

        @Suppress("UNCHECKED_CAST")
        val pivot: T = array[lo] as T

        var i = lo
        var lt = lo
        var gt = hi

        while (i <= gt) {
            val cmp = array[i].compareTo(pivot)

            when {
                cmp < 0 -> exch(array, lt++, i++)
                cmp > 0 -> exch(array, i, gt--)
                else -> i++
            }
        }

        val times = (gt - lt) + 1

        if (times >= array.size / 10)
            println(pivot)

        sort(array, lo, lt - 1)
        sort(array, gt + 1, hi)
    }

    private fun exch(array: Array<Comparable<T>>, i: Int, j: Int) {
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }
}

fun main(args: Array<String>) {
    val array = arrayOf<Comparable<Int>>(1, 2, 1, 2, 2, 2, 1, 2, 1, 4, 3, 2, 4, 2, 3, 5, 6, 7, 8, 9, 3, 2, 4, 5, 2, 3, 6, 7, 8, 3, 2, 5)

    println(array.size / 10)

    ThreeWayQuickSort<Int>().sort(array, 0, array.lastIndex)

    println(array.joinToString(separator = " "))
}