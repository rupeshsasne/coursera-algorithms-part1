package com.radix2.algorithms.week4

class MaxPQ<T : Comparable<T>>(capacity: Int) {
    private var array = Array<Comparable<Any>?>(capacity + 1) { null }

    private var n = 0

    companion object {

        private fun sink(array: Array<Comparable<Any>>, k: Int, n: Int) {
            var trav = k

            while (2 * trav <= n) {
                var j = 2 * trav

                if (j < n && less(array, j, j + 1))
                    j++

                if (!less(array, k, j)) break

                exch(array, k, j)

                trav = j
            }
        }

        private fun swim(array: Array<Comparable<Any>>, k: Int) {
            var trav = k

            while (trav > 1 && less(array,trav / 2, trav)) {
                exch(array, trav, trav / 2)
                trav /= 2
            }
        }

        private fun exch(array: Array<Comparable<Any>>, i: Int, j: Int) {
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
        }

        private fun less(array: Array<Comparable<Any>>, lhs: Int, rhs: Int): Boolean = array[lhs] < (array[rhs])
    }

    @Suppress("UNCHECKED_CAST")
    fun add(t: T) {
        array[++n] = t as Comparable<Any>
        swim(array as Array<Comparable<Any>>, n)
    }

    @Suppress("UNCHECKED_CAST")
    fun deleteMax(): T {
        val key = array[1]

        exch(array as Array<Comparable<Any>>, 1, n--)
        sink(array as Array<Comparable<Any>>, 1, n)
        array[n + 1] = null

        @Suppress("UNCHECKED_CAST")
        return key as T
    }

    fun size() = n

    override fun toString(): String = array.filter { it != null }.joinToString()
}

fun main(args: Array<String>) {
    val maxPQ = MaxPQ<Int>(20)

    val list = mutableListOf(16, 14, 2, 19, 13, 18, 1, 10, 5, 4, 3, 6, 20, 7, 9, 11, 12, 15, 17, 8)

    list.shuffle()

    for (i in 0 until 20) {
        maxPQ.add(list[i])
    }

    println(maxPQ.deleteMax())
    println(maxPQ.deleteMax())
    println(maxPQ.deleteMax())
}
