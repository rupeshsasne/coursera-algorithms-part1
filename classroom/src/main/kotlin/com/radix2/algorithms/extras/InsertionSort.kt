package com.radix2.algorithms.extras

import java.util.*

fun insertionSort2(n: Int, arr: Array<Int>): Unit {

    for (i in arr.lastIndex downTo 0) {
        val key = arr[i]

        var j: Int = i - 1

        while (j >= 0) {
            if (arr[j] > key) {
                arr[j + 1] = arr[j]
                j--
            } else {
                break
            }
        }

        arr[j + 1] = key

        println(arr.joinToString(" "))
    }
}

fun insertionSort1(n: Int, arr: Array<Int>): Unit {
    val key = arr[arr.lastIndex]

    var i: Int = arr.lastIndex - 1

    while (i >= 0) {
        if (arr[i] > key) {
            arr[i + 1] = arr[i]
            println(arr.joinToString(" "))
            i--
        } else {
            break
        }
    }

    arr[i + 1] = key

    println(arr.joinToString(" "))
}

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)

    val n = scan.nextLine().trim().toInt()

    val arr = scan.nextLine().split(" ").map{ it.trim().toInt() }.toTypedArray()

    insertionSort2(n, arr)
}
