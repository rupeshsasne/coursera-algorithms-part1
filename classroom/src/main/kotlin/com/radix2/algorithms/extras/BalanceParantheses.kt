package com.radix2.algorithms.extras

fun isBalanced(str: String): Int {

    var counter = 0

    for (i in 0..str.lastIndex) {
        if (str[i] == '(') {
            counter++
        } else {
            counter--
        }

        if (counter < 0) {
            return i
        }
    }

    return if (counter == 0) -1 else { str.length - counter }
}

fun main() {

    println("""
    ${isBalanced("((())())()")}
    ${isBalanced(")()(")}
    ${isBalanced("())(((")}
    """.trimIndent())

}