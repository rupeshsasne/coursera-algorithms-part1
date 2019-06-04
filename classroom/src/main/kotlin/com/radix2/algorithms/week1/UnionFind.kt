package com.radix2.algorithms.week1

interface UF {
    fun union(p: Int, q: Int)

    fun connected(p: Int, q: Int): Boolean
}

class QuickFindUF(size: Int) : UF {
    private val ids: IntArray = IntArray(size) { it }

    override fun connected(p: Int, q: Int): Boolean = ids[p] == ids[q]

    override fun union(p: Int, q: Int) {
        val pId = ids[p]
        val qId = ids[q]

        for (i in 0 until ids.size) {
            if (ids[i] == pId) {
                ids[i] = qId
            }
        }
    }
}

open class QuickUnionUF(size: Int) : UF {
    protected val ids: IntArray = IntArray(size) { it }

    override fun union(p: Int, q: Int) {
        val pRoot = root(p)
        val qRoot = root(q)

        ids[pRoot] = qRoot
    }

    override fun connected(p: Int, q: Int): Boolean = root(p) == root(q)

    protected open fun root(p: Int): Int {
        var i = p

        while (i != ids[i]) {
            i = ids[i]
        }

        return i
    }
}

open class WeightedQuickUnionUF(size: Int) : QuickUnionUF(size) {
    protected val size: IntArray = IntArray(size) { 1 }

    override fun union(p: Int, q: Int) {
        val pRoot = root(p)
        val qRoot = root(q)

        if (pRoot == qRoot) return

        if (size[pRoot] < size[qRoot]) {
            ids[pRoot] = ids[qRoot]
            size[qRoot] += size[pRoot]
        } else {
            ids[qRoot] = ids[pRoot]
            size[pRoot] += size[qRoot]
        }
    }
}

class WeightedQuickUnionWithPathCompressionUF(size: Int) : WeightedQuickUnionUF(size) {
    override fun root(p: Int): Int {
        var i = p

        while (i != ids[i]) {
            ids[i] = ids[ids[i]]
            i = ids[i]
        }

        return i
    }
}

fun main(args: Array<String>) {
    val uf = WeightedQuickUnionWithPathCompressionUF(10)

    uf.union(4, 3)
    uf.union(3, 8)
    uf.union(6, 5)
    uf.union(9, 4)
    uf.union(2, 1)
    uf.union(5, 0)
    uf.union(7, 2)
    uf.union(6, 1)
    uf.union(7, 3)

    //println(uf.find(3))
}