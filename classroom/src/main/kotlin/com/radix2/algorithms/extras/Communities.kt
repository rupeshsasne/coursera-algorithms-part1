package com.radix2.algorithms.extras

import kotlin.collections.*

class WeightedQuickUnionWithPathCompressionUF<T : Any> {

    private val map = mutableMapOf<T, Metadata<T>>()

    private class Metadata<E : Any> {
        lateinit var parent: E
        var size = 1
    }

    fun union(p: T, q: T): Int {
        var pRoot = root(p)
        var qRoot = root(q)

        if (pRoot == qRoot) return map[pRoot]!!.size

        if (map[pRoot]!!.size < map[qRoot]!!.size) {
            val temp = pRoot
            pRoot = qRoot
            qRoot = temp
        }

        map[qRoot]!!.parent = pRoot
        map[pRoot]!!.size += map[qRoot]!!.size

        return map[pRoot]!!.size
    }

    fun size(p: T): Int = map[root(p)]!!.size

    private fun root(p: T): T {
        var metadata = getMetadata(p)

        while (metadata.parent != map[metadata.parent]!!.parent) {
            metadata.parent = map[map[metadata.parent]!!.parent]!!.parent
            metadata = map[metadata.parent]!!
        }

        return metadata.parent
    }

    private fun getMetadata(p: T): Metadata<T> {
        if (!map.contains(p)) {
            val metadata = Metadata<T>()
            metadata.parent = p
            metadata.size = 1

            map[p] = metadata
        }

        return map[p]!!
    }
}

fun main(args: Array<String>) {
    val start = System.nanoTime()

    val reader = System.`in`.bufferedReader()

    var (_, q) = reader.readLine().split(" ").map { it.toInt() }

    val uf = WeightedQuickUnionWithPathCompressionUF<String>()

    while (q-- > 0) {

        val line = reader.readLine()

        if (line.startsWith("M")) {
            val (p1, p2) = line.substring(2).split(" ").map { it.toInt() }
            uf.union(p1.toString(), p2.toString())
        } else {
            val p = line.substring(2).toInt()
            println("${uf.size(p.toString())}")
        }
    }

    reader.close()

    val end = System.nanoTime()

    println("${end - start}")
}