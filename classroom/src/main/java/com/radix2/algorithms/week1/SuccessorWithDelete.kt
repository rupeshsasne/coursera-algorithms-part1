package com.radix2.algorithms.week1

class SuccessorWithDelete(size: Int) {
    val uf = UFWithFindLargest(size)

    fun remove(x: Int) {
        uf.union(x, x + 1)
    }

    fun successor(x: Int): Int {
        return uf.find(x + 1)
    }
}

fun main(args: Array<String>) {
    val swd = SuccessorWithDelete(100)

    swd.remove(10)
    swd.remove(12)
    swd.remove(11)
    swd.remove(9)

    println(swd.successor(7))
}
