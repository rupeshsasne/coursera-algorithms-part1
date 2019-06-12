package com.radix2.algorithms.extras

fun main(args: Array<String>) {
    val reader = System.`in`.bufferedReader()

    val (n, q) = reader.readLine().split(" ").map { it.toInt() }

    val list: MutableList<MutableSet<Int>> = mutableListOf()

    for (i in 1..n)
        list.add(mutableSetOf(i))

    for (i in 0 until q) {

        val line = reader.readLine()

        if (line.startsWith("M")) {
            val (p1, p2) = line.substring(2).split(" ").map { it.toInt() }

            var s1 = list.first { it.contains(p1) }
            var s2 = list.first { it.contains(p2) }

            if (s1 === s2) continue

            if (s1.size < s2.size) {
                val temp = s1
                s1 = s2
                s2 = temp
            }

            s1.addAll(s2)

            s2.clear()
        } else {
            val p = line.substring(2).toInt()

            println("${list.first { it.contains(p) }.size}")
        }
    }

    reader.close()
}
