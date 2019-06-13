package com.radix2.algorithms.week3

fun sort(array: Array<Int>): Long {
    return sort(array, Array(array.size) { 0 }, 0, array.size - 1)
}

fun sort(array: Array<Int>, aux: Array<Int>, lo: Int, hi: Int): Long {
    if (lo >= hi) return 0

    val mid = lo + (hi - lo) / 2

    var inversions: Long

    inversions = sort(array, aux, lo, mid)
    inversions += sort(array, aux, mid + 1, hi)

    return inversions + merge(array, aux, lo, mid, hi)
}

fun merge(array: Array<Int>, aux: Array<Int>, lo: Int, mid: Int, hi: Int): Long {
    var i = lo
    var j = mid + 1
    val nextToMid = j
    var k = lo

    var inversions = 0L

    while(i <= mid && j <= hi) {
        if (array[i] <= array[j]) {
            aux[k++] = array[i++]
        } else {
            aux[k++] = array[j++]
            inversions += nextToMid - i
        }
    }

    while(i <= mid)
        aux[k++] = array[i++]

    while(j <= hi)
        aux[k++] = array[j++]

    System.arraycopy(aux, lo, array, lo, (hi - lo) + 1)

    return inversions
}

fun main(args: Array<String>) {

    val array = arrayOf(7, 5, 3, 1)

    val inversions = sort(array)

    println(array.joinToString())
    println(inversions)
}
