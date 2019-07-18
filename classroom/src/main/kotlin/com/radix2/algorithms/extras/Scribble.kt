package com.radix2.algorithms.extras

import java.util.*

fun print(array: IntArray, lo: Int, hi: Int) {
    if (lo == hi) {
        println(array[lo])
    } else {
        println(array[lo])
        print(array, lo + 1, hi)
    }
}

fun map(array: IntArray, lo: Int, hi: Int, transform: (Int) -> Int) {
    array[lo] = transform(lo)

    if (lo < hi)
        map(array, lo + 1, hi, transform)
}

fun fib(n: Int): Int {
    return if (n < 2)
        n
    else
        fib(n - 1) + fib(n - 2)
}

fun towerOfHanoi(num: Int, from: Char, to: Char, aux: Char) {
    if (num == 1) {
        println("$from -> $to")
    } else {

        towerOfHanoi(num - 1, from, aux, to)

        println("$from -> $to")

        towerOfHanoi(num - 1, aux, to, from)
    }
}

fun isPallindrom(str: String, lo: Int, hi: Int): Boolean {
    if (hi <= lo)
        return str[lo] == str[hi]

    return str[lo] == str[hi] && isPallindrom(str, lo + 1, hi - 1)
}

fun power(a: Int, b: Int): Int {
    if (b == 1)
        return a

    return a * power(a, b - 1)
}

fun reversePrint(str: String) {
    if (str.length == 1)
        print(str)
    else {
        print("${str.last()}")
        reversePrint(str.substring(0, str.lastIndex))
    }
}

fun shiftBlankToRight(array: IntArray) {
    val list = array.sortedWith(kotlin.Comparator { o1, o2 ->
        when {
            o1 == ' '.toInt() -> 1
            o2 == ' '.toInt() -> -1
            else -> 0
        }
    })

    println(list.joinToString())
}

fun main(args: Array<String>) {
    val array = intArrayOf('A'.toInt(), 'B'.toInt(), ' '.toInt(), 'C'.toInt(), ' '.toInt(), ' '.toInt(), 'D'.toInt())

    shiftBlankToRight(array)

    println()
}
