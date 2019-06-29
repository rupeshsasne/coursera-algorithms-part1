package com.radix2.algorithms.week4

class MaxPQ<T : Comparable<T>>(capacity: Int) {
    private var array = Array<Comparable<Any>?>(capacity) { null }

    private var n = 0

    companion object {

        @Suppress("UNCHECKED_CAST")
        fun <T : Comparable<T>> sort(array: Array<Comparable<T>>) {
            for (i in array.lastIndex / 2 downTo 0) {
                sink(array as Array<Comparable<Any>>, i, array.lastIndex)
            }

            var n = array.lastIndex

            while (n > 0) {
                exch(array as Array<Comparable<Any>>, 0, n)
                sink(array as Array<Comparable<Any>>, 0, n--)
            }
        }

        private fun sink(array: Array<Comparable<Any>>, k: Int, n: Int) {
            var trav = k

            while (2 * trav + 1 < n) {
                var j = 2 * trav + 1

                if (j < n - 1 && less(array, j, j + 1))
                    j++

                if (!less(array, trav, j)) break

                exch(array, trav, j)

                trav = j
            }
        }

        private fun swim(array: Array<Comparable<Any>>, k: Int) {
            var trav = k

            while (trav > 0 && less(array,trav / 2, trav)) {
                exch(array, trav, trav / 2)
                trav /= 2
            }
        }

        private fun exch(array: Array<Comparable<Any>>, i: Int, j: Int) {
            val temp = array[i]
            array[i] = array[j]
            array[j] = temp
        }

        private fun less(array: Array<Comparable<Any>>, lhs: Int, rhs: Int): Boolean = array[lhs] < array[rhs]
    }

    @Suppress("UNCHECKED_CAST")
    fun add(t: T) {
        array[n++] = t as Comparable<Any>
        swim(array as Array<Comparable<Any>>, n - 1)
    }

    @Suppress("UNCHECKED_CAST")
    fun deleteMax(): T {
        val key = array[0]

        exch(array as Array<Comparable<Any>>,0, --n)
        sink(array as Array<Comparable<Any>>,0, n)
        array[n] = null

        return key as T
    }

    fun size() = n

    override fun toString(): String = array.filter { it != null }.joinToString()
}

fun main(args: Array<String>) {

    val maxPQ = MaxPQ<Int>(20)

    val list = mutableListOf(9, 2, 3, 8, 1, 10, 6, 17, 5, 16, 18, 12, 14, 11, 15, 20, 7, 4, 19, 13)

    for (i in 0 until 20) {
        maxPQ.add(list[i])
    }

    println(maxPQ)

    val array: Array<Comparable<Int>> = list.toTypedArray()

    MaxPQ.sort(array)

    println(array.joinToString())
}