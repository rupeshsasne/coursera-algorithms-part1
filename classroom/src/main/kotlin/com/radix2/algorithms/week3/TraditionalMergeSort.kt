package com.radix2.algorithms.week3

fun IntArray.mergeSort(comparator: (Int, Int) -> Int) {
    val aux = IntArray(size)
    sort(this, aux, 0, size - 1, comparator)
}

fun sort(array: IntArray, aux: IntArray, lo: Int, hi: Int, comparator: (Int, Int) -> Int) {
    if (lo >= hi) return

    val mid = lo + (hi - lo) / 2

    sort(array, aux, lo, mid, comparator)
    sort(array, aux, mid + 1, hi, comparator)
    merge(array, aux, lo, mid, hi, comparator)
}

fun merge(array: IntArray, aux: IntArray, lo: Int, mid: Int, hi: Int, comparator: (Int, Int) -> Int) {
    System.arraycopy(array, lo, aux, lo, (hi - lo) + 1)

    var i = lo
    var j = mid + 1

    for (k in lo..hi) {
        when {
            i > mid -> array[k] = aux[j++]
            j > hi -> array[k] = aux[i++]
            comparator(aux[i], aux[j]) > 0 -> array[k] = aux[i++]
            else -> array[k] = aux[j++]
        }
    }
}

fun main(args: Array<String>) {
    val array = intArrayOf(6, 2, 1, 0, 13, 89, 72, 4, 22, 28)
    array.mergeSort { t1, t2 -> if (t1 < t2) -1 else 1 }
    println(array.joinToString())
}