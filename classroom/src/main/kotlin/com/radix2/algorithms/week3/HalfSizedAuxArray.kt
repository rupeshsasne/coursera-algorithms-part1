package com.radix2.algorithms.week3

// Merging with smaller auxiliary array.
// Suppose that the sub array 𝚊[𝟶] to 𝚊[𝚗−𝟷] is sorted and the subarray 𝚊[𝚗] to 𝚊[𝟸 ∗ 𝚗 − 𝟷] is sorted.
// How can you merge the two sub arrays so that 𝚊[𝟶] to 𝚊[𝟸 ∗ 𝚗−𝟷] is sorted using an auxiliary array of length n (instead of 2n)?

fun mergeWithSmallerAux(array: IntArray, aux: IntArray = IntArray(array.size / 2), lo: Int, mid: Int, hi: Int) {
    System.arraycopy(array, lo, aux, lo, aux.size)

    var i = lo
    var j = mid + 1

    var k = 0

    while(i <= mid && j <= hi) {
        if (aux[i] > array[j]) {
            array[k++] = array[j++]
        } else {
            array[k++] = aux[i++]
        }
    }

    while(i <= mid)
        array[k++] = aux[i++]

    while(j <= hi)
        array[k++] = array[j++]
}

fun main(args: Array<String>) {

    val array = intArrayOf(1, 3, 5, 7, 9, 11, 0, 2, 4, 6, 8, 10)

    mergeWithSmallerAux(array, lo = 0, mid = (array.size - 1) / 2, hi = array.size - 1)

    println(array.joinToString())
}