package com.radix2.algorithms.week4

import java.util.*

interface SymbolTable<K : Comparable<K>, V> {
    fun put(key: K, value: V)

    fun get(key: K): V?

    fun min(): Pair<K, V>?

    fun max(): Pair<K, V>?

    fun floor(key: K): Pair<K, V>?

    fun ceil(key: K): Pair<K, V>?

    fun size(): Int

    fun rank(key: K): Int

    fun deleteMin()

    fun deleteMax()

    fun delete(key: K)

    override fun toString(): String
}

class BST<K : Comparable<K>, V> : SymbolTable<K, V> {
    private class Node<K : Any, V>(
        var key: K,
        var data: V,
        var left: Node<K, V>? = null,
        var right: Node<K, V>? = null,
        var count: Int = 1
    ) {
        override fun toString(): String = (key to data).toString()
    }

    private var root: Node<K, V>? = null

    override fun put(key: K, value: V) {
        root = put(root, key, value)
    }

    override fun get(key: K): V? = get(root, key)?.data

    override fun min(): Pair<K, V>? = min(root)?.let { it.key to it.data }

    override fun max(): Pair<K, V>? = max(root)?.let { it.key to it.data }

    override fun floor(key: K): Pair<K, V>? = floor(root, key)?.let { it.key to it.data }

    override fun ceil(key: K): Pair<K, V>? = ceil(root, key)?.let { it.key to it.data }

    override fun size(): Int = root?.count ?: 0

    override fun rank(key: K): Int = rank(root, key)

    override fun deleteMin() {
        deleteMin(root)
    }

    override fun deleteMax() {
        deleteMax(root)
    }

    override fun delete(key: K) {
        delete(root, key)
    }

    override fun toString(): String = buildString {
        levelOrder(root, this)

        //inOrderWithStack(root, this)
        //inOrderRecursive(root, this)
    }

    private fun put(root: Node<K, V>?, key: K, value: V): Node<K, V>? {
        if (root == null)
            return Node(key, value)

        val cmp = key.compareTo(root.key)

        when {
            cmp < 0 -> root.left = put(root.left, key, value)

            cmp > 0 -> root.right = put(root.right, key, value)

            else -> root.data = value
        }

        root.count = 1 + (root.left?.count ?: 0) + (root.right?.count ?: 0)

        return root
    }

    private fun get(root: Node<K, V>?, key: K): Node<K, V>? {
        if (root == null)
            return root

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

    private fun rank(root: Node<K, V>?, key: K): Int {
        if (root == null)
            return 0

        val cmp = key.compareTo(root.key)

        return when {
            cmp == 0 -> root.left?.count ?: 0

            cmp < 0 -> rank(root.left, key)

            else -> 1 + (root.left?.count ?: 0) + rank(root.right, key)
        }
    }

    private fun deleteMin(root: Node<K, V>?): Node<K, V>? {
        if (root?.left == null)
            return root?.right

        root.left = deleteMin(root.left)

        root.count = 1 + (root.left?.count ?: 0) + (root.right?.count ?: 0)

        return root
    }

    private fun deleteMax(root: Node<K, V>?): Node<K, V>? {
        if (root?.right == null)
            return root?.left

        root.right = deleteMax(root.right)

        root.count = 1 + (root.right?.count ?: 0) + (root.left?.count ?: 0)

        return root
    }

    private fun delete(root: Node<K, V>?, key: K): Node<K, V>? {
        if (root == null)
            return null

        var trav = root

        val cmp = key.compareTo(trav.key)

        when {
            cmp < 0 -> trav.left = delete(trav.left, key)
            cmp > 0 -> trav.right = delete(trav.right, key)

            else -> {
                if (trav.left == null)
                    return trav.right

                if (trav.right == null)
                    return trav.left

                val temp = trav

                trav = min(temp.right)

                trav?.right = deleteMin(temp.right)

                trav?.left = temp.left
            }
        }

        trav?.count = 1 + (trav?.left?.count ?: 0) + (trav?.right?.count ?: 0)

        return trav
    }

    private fun inOrderRecursive(root: Node<K, V>?, builder: StringBuilder) {
        if (root == null)
            return

        inOrderRecursive(root.left, builder)

        builder.append(root.key to root.data)

        inOrderRecursive(root.right, builder)
    }

    private fun inOrderWithStack(root: Node<K, V>?, builder: StringBuilder) {
        if (root == null)
            return

        var trav = root

        val stack = Stack<Node<K, V>>()

        while (trav != null || !stack.isEmpty()) {
            while (trav != null) {
                stack.push(trav)
                trav = trav.left
            }

            val top = stack.pop()

            builder.append(top.key to top.data)

            trav = top.right
        }
    }

    private fun levelOrder(root: Node<K, V>?, builder: StringBuilder) {
        if (root == null)
            return

        val queue: Queue<Node<K, V>> = LinkedList()

        queue.add(root)

        with(queue) {
            while (!queue.isEmpty()) {

                if (builder.isNotEmpty()) {
                    builder.append(", ")
                }

                builder.append(peek())

                val front = queue.poll()

                if (front.left != null) {
                    add(front.left)
                }

                if (front.right != null) {
                    add(front.right)
                }
            }
        }
    }
}

fun <K : Comparable<K>, V> SymbolTable<K, V?>.put(key: K) = this.put(key, null)

fun main() {
    val st: SymbolTable<Int, String?> = BST()
    st.put(6)

    st.put(7)

    st.put(4)

    st.put(1)

    println(st)
}