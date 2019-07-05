package com.radix2.algorithms.extras

import java.util.*
import java.time.Duration
import java.time.Instant

class Taxicab(var n1: Int, var n2: Int) : Comparable<Taxicab> {
    var cube: Int = 0

    init {
        this.cube = n1 * n1 * n1 + n2 * n2 * n2
    }

    override fun compareTo(that: Taxicab): Int {
        if (that.cube > this.cube) return -1
        return if (that.cube < this.cube) 1 else 0
    }

    override fun equals(o: Any?): Boolean {
        if (o is Taxicab) {
            if (o.compareTo(this) == 0)
                return true
        }
        return false
    }

    override fun toString(): String {
        return "number: $cube ($n1, $n2)"
    }

    override fun hashCode(): Int {
        return Objects.hashCode(cube)
    }
}

fun findTaxiNumbers(n: Int) {
    val pq = PriorityQueue<Taxicab>()

    for (i in 1..n) {
        for (j in (i + 1)..n) {
            pq.add(Taxicab(i, j))
        }
    }

    while (pq.size >= 2) {
        val lhs = pq.poll()

        while (!pq.isEmpty() && lhs == pq.peek()) {
            println("$lhs = ${pq.poll()}")
        }
    }
}

fun main(args: Array<String>) {
    val start = Instant.now()

    findTaxiNumbers(100)

    val finish = Instant.now()
    val timeElapsedV1 = Duration.between(start, finish).toMillis()

    println("V1 = $timeElapsedV1")
}