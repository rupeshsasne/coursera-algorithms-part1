package com.radix2.algorithms.extras

fun findMatch(str: String, word: String): Int {
    val m = str.length
    val n = word.length

    for (i in 0..(m - n)) {
        var j = 0

        while (j < n && str[i + j] == word[j])
            j++

        if (j == n) return i
    }

    return -1
}

fun main(args: Array<String>) {

}