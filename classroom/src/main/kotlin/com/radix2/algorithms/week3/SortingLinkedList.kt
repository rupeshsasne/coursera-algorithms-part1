package com.radix2.algorithms.week3

import kotlin.random.Random

class LinkedList<T : Comparable<T>> {
    private var head: Node<T>? = null
    private val toss = intArrayOf(-1, 1)

    fun add(data: T) {
        val n = Node(data)
        n.next = head
        head = n
    }


    fun sort() {
        head = sort(head)
    }

    fun shuffle() {
        head = sort(head) { _, _ ->
            val index = Math.abs(Random.nextInt()) % 2
            toss[index]
        }
    }

    override fun toString() = buildString {
        var trav = head

        while (trav != null) {

            if (length > 0)
                append(", ")

            append(trav.data)

            trav = trav.next
        }
    }

    private fun sort(
        head: Node<T>?,
        comparator: (lhs: T, rhs: T) -> Int = { lhs, rhs -> lhs.compareTo(rhs) }
    ): Node<T>? {
        if (head?.next == null)
            return head

        val mid = getMiddleNode(head)
        val nextToMid = mid?.next
        mid?.next = null

        val left = sort(head)
        val right = sort(nextToMid)

        return merge(left, right, comparator)
    }

    private fun getMiddleNode(head: Node<T>?): Node<T>? {
        if (head == null) return null

        var slow = head
        var fast = head.next

        while (fast != null) {
            fast = fast.next

            if (fast != null) {
                fast = fast.next
                slow = slow?.next
            }
        }

        return slow
    }

    private fun merge(left: Node<T>?, right: Node<T>?, comparator: (lhs: T, rhs: T) -> Int): Node<T>? {
        if (left == null)
            return right

        if (right == null)
            return left

        val result: Node<T>?

        if (comparator(left.data, right.data) > 0) {
            result = right
            result.next = merge(left, right.next, comparator)
        } else {
            result = left
            result.next = merge(left.next, right, comparator)
        }

        return result
    }

    private class Node<E>(val data: E, var next: Node<E>? = null)
}

fun main(args: Array<String>) {

    val linkedList = LinkedList<Int>()

    for (i in 0..1000) {
        linkedList.add(i)
    }

    linkedList.shuffle()
    linkedList.sort()
}
