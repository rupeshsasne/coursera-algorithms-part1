package com.radix2.algorithms.week5

import com.radix2.algorithms.week4.SymbolTable
import com.radix2.algorithms.week4.put
import java.lang.StringBuilder
import java.util.*

class LLRBTree<K : Comparable<K>, V> : SymbolTable<K, V> {

    private var root: Node<K, V>? = null

    private enum class Color { RED, BLACK }

    private class Node<K, V>(
        var key: K,
        var value: V,
        var left: Node<K, V>? = null,
        var right: Node<K, V>? = null,
        var color: Color = Color.RED
    )

    override fun put(key: K, value: V) {
        root = put(root, Node(key, value))
    }

    override fun get(key: K): V? = get(root, key)?.value

    override fun min(): Pair<K, V>? = min(root)?.let { it.key to it.value }

    override fun max(): Pair<K, V>? = max(root)?.let { it.key to it.value }

    override fun floor(key: K): Pair<K, V>? = floor(root, key)?.let { it.key to it.value }

    override fun ceil(key: K): Pair<K, V>? = ceil(root, key)?.let { it.key to it.value }

    override fun toString(): String = levelOrder(root)

    private fun getNodesAtLevel(root: Node<K, V>?, level: Int, builder: StringBuilder) {
        if (root == null) return

        if (level == 1) {
            builder.append(root.key to root.value)
        }

        getNodesAtLevel(root.left, level - 1, builder)
        getNodesAtLevel(root.right, level - 1, builder)
    }

    private fun height(root: Node<K, V>?): Int {
        if (root == null)
            return 0

        return 1 + kotlin.math.max(height(root.left), height(root.right))
    }

    // M, E, R, C, L, P, X, A, H, S
    // M, E, R, C, L, P, X, A, H, S
    private fun levelOrder(root: Node<K, V>?): String {
        if (root == null)
            return ""

        val queue: Queue<Node<K, V>> = LinkedList()

        queue.add(root)

        return buildString {
            while (!queue.isEmpty()) {
                if (length > 0)
                    append(", ")

                append(queue.peek().key)

                val node = queue.poll()

                if (node.left != null)
                    queue.add(node.left)

                if (node.right != null)
                    queue.add(node.right)
            }
        }
    }

    private fun levelOrder(root: Node<K, V>?, height: Int, builder: StringBuilder) {

        for (i in 1..height) {
            getNodesAtLevel(root, i, builder)
        }
    }

    private fun inOrder(root: Node<K, V>?, builder: StringBuilder) {
        if (root == null)
            return

        inOrder(root.left, builder)

        builder.append(root.key to root.value)

        inOrder(root.right, builder)
    }

    private fun put(root: Node<K, V>?, node: Node<K, V>): Node<K, V>? {
        if (root == null)
            return node

        var trav = root

        val cmp = node.key.compareTo(trav.key)

        when {
            cmp < 0 -> trav.left = put(trav.left, node)

            cmp > 0 -> trav.right = put(trav.right, node)

            else -> trav.value = node.value
        }

        when {
            trav.right.isRed() && !trav.left.isRed() -> trav = rotateLeft(trav)

            trav.left.isRed() && trav.left?.left.isRed() -> trav = rotateRight(trav)

            trav.left.isRed() && trav.right.isRed() -> flipColors(trav)
        }

        return trav
    }

    private fun get(root: Node<K, V>?, key: K): Node<K, V>? {
        if (root == null)
            return null

        val cmp = key.compareTo(root.key)

        return when {
            cmp < 0 -> get(root.left, key)

            cmp > 0 -> get(root.right, key)

            else -> root
        }
    }

    private fun min(root: Node<K, V>?): Node<K, V>? {
        if (root?.left == null)
            return root

        return min(root.left)
    }

    private fun max(root: Node<K, V>?): Node<K, V>? {
        if (root?.right == null)
            return root

        return max(root.right)
    }

    private fun floor(root: Node<K, V>?, key: K): Node<K, V>? {
        if (root == null)
            return null

        val cmp = key.compareTo(root.key)

        return when {
            cmp == 0 -> root

            cmp < 0 -> floor(root.left, key)

            else -> floor(root.right, key) ?: root
        }
    }

    private fun ceil(root: Node<K, V>?, key: K): Node<K, V>? {
        if (root == null)
            return null

        val cmp = key.compareTo(root.key)

        return when {
            cmp == 0 -> root

            cmp < 0 -> ceil(root.left, key) ?: root

            else -> ceil(root.right, key)
        }
    }

    private fun Node<K, V>?.isRed() = (this?.color ?: Color.BLACK) == Color.RED

    private fun rotateLeft(node: Node<K, V>): Node<K, V>? {
        assert(node.isRed())
        val trav = node

        val temp = node.right
        trav.right = temp?.left
        temp?.left = trav
        temp?.color = trav.color
        trav.color = Color.RED
        return temp
    }

    private fun rotateRight(node: Node<K, V>): Node<K, V>? {
        assert(node.isRed())
        val trav = node

        val temp = node.left
        trav.left = temp?.right
        temp?.right = trav
        temp?.color = trav.color
        trav.color = Color.RED
        return temp
    }

    private fun flipColors(node: Node<K, V>) {
        assert(!node.isRed())
        assert(node.left.isRed())
        assert(node.right.isRed())

        node.color = Color.RED
        node.left?.color = Color.BLACK
        node.right?.color = Color.BLACK
    }

    override fun deleteMin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun rank(key: K): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun size(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteMax() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(key: K) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun main() {
    val rbt = LLRBTree<Char, String?>()
    rbt.put('S')
    rbt.put('E')
    rbt.put('A')
    rbt.put('R')
    rbt.put('C')
    rbt.put('H')
    rbt.put('X')
    rbt.put('M')
    rbt.put('P')
    rbt.put('L')

    println(rbt)
}