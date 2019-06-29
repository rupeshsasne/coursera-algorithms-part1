package com.radix2.algorithms.week4

class MaxPQ<T : Comparable<T>>(capacity: Int) {
    private var array = Array<Any?>(capacity) { null }

    private var n = 0

    fun add(t: T) {
        array[n++] = t
        swim(n - 1)
    }

    fun deleteMax(): T {
        val key = array[0]

        exch(0, --n)
        sink(0)
        array[n] = null

        @Suppress("UNCHECKED_CAST")
        return key as T
    }

    fun size() = n

    private fun sink(k: Int) {
        var trav = k

        while (2 * trav + 1 < n) {
            var j = 2 * trav + 1

            if (j < n - 1 && less(j, j + 1))
                j++

            if (!less(trav, j)) break

            exch(trav, j)

            trav = j
        }
    }

    private fun swim(k: Int) {
        var trav = k

        while (trav > 0 && less(trav / 2, trav)) {
            exch(trav, trav / 2)
            trav /= 2
        }
    }

    private fun exch(i: Int, j: Int) {
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    @Suppress("UNCHECKED_CAST")
    private fun less(lhs: Int, rhs: Int): Boolean = (array[lhs] as Comparable<T>) < (array[rhs] as T)

    override fun toString(): String = array.filter { it != null }.joinToString()
}

fun main(args: Array<String>) {

    val maxPQ = MaxPQ<Int>(20)

    val list = mutableListOf(9, 2, 3, 8, 1, 10, 6, 17, 5, 16, 18, 12, 14, 11, 15, 20, 7, 4, 19, 13)

    list.shuffle()

    for (i in 0 until 20) {
        maxPQ.add(list[i])
    }

    for (i in 0 until 20) {
        println(maxPQ.deleteMax())
    }
}