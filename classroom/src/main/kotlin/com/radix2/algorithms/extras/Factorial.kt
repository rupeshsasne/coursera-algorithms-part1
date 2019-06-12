package com.radix2.algorithms.extras

import java.math.BigInteger
import java.util.*

fun extraLongFactorials(n: BigInteger): BigInteger {
    if (n == BigInteger.ONE)
        return BigInteger.ONE

    return n.multiply(extraLongFactorials(n.minus(BigInteger.ONE)))
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val n = scan.nextLine().trim().toLong()

    println(extraLongFactorials(BigInteger.valueOf(n)).toString())
}