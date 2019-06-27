package com.radix2.algorithms.week3

import java.util.*

class NutsAndBolts {

    fun solve(nuts: IntArray, bolts: IntArray) {
        shuffle(nuts)
        shuffle(bolts)

        solve(nuts, bolts, 0, nuts.size - 1)
    }

    private fun solve(nuts: IntArray, bolts: IntArray, lo: Int, hi: Int) {
        if (lo >= hi) return

        val pivot = partition(nuts, bolts[lo], lo, hi)
        partition(bolts, nuts[pivot], lo, hi)

        solve(nuts, bolts, lo, pivot - 1)
        solve(nuts, bolts, pivot + 1, hi)
    }

    private fun partition(array: IntArray, pivot: Int, lo: Int, hi: Int): Int {
        var i = lo
        var j = hi + 1

        while (true) {
            while (array[++i] <= pivot) {
                if (i == hi) break

                if (array[i] == pivot) {
                    exch(array, lo, i)
                    i--
                }
            }

            while (pivot <= array[--j]) {
                if (j == lo) break

                if (array[j] == pivot) {
                    exch(array, lo, j)
                    j++
                }
            }

            if (i >= j) break

            exch(array, i, j)
        }

        exch(array, lo, j)

        return j
    }

    private fun exch(array: IntArray, i: Int, j: Int) {
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    private fun shuffle(array: IntArray) {
        val rnd = Random()

        for (i in 1 until array.size) {
            val randomIndex = rnd.nextInt(i)
            exch(array, i, randomIndex)
        }
    }
}

fun main(args: Array<String>) {

    val nuts = intArrayOf(5, 3, 7, 9, 2, 8, 4, 1, 6, 0, 10, 11, 12, 13, 14, 15, 16, 17)
    val bolts = intArrayOf(4, 7, 1, 6, 3, 0, 8, 9, 2, 5, 10, 11, 12, 13, 14, 15, 16, 17)

    NutsAndBolts().solve(nuts, bolts)

    println("""
        Nuts    : ${nuts.joinToString(separator = ", ")}
        Bolts   : ${bolts.joinToString(separator = ", ")}
    """.trimIndent())
}