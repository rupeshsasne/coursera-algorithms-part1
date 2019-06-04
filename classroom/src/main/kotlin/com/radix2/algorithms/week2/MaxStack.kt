package com.radix2.algorithms.week2

class MaxStack {
    private class Node(val data: Int, var next: Node?)

    private var stackTop: Node? = null

    private var maxStackTop: Node? = null

    fun push(item: Int) {
        val oldTop = stackTop
        stackTop = Node(item, null)
        stackTop?.next = oldTop

        val oldMax = maxStackTop

        if (stackTop?.data ?: 0 > oldMax?.data ?: Integer.MIN_VALUE) {
            maxStackTop = Node(item, null)
            maxStackTop?.next = oldMax
        }
    }

    fun pop(): Int? {
        val oldTop = stackTop
        stackTop = stackTop?.next

        val oldMax = maxStackTop

        if (oldMax?.data == oldTop?.data) {
            maxStackTop = maxStackTop?.next
        }

        return oldTop?.data
    }

    fun max() = maxStackTop?.data
}

fun main(args: Array<String>) {

    val maxStack = MaxStack()

    maxStack.push(1)
    maxStack.push(2)
    maxStack.push(3)
    maxStack.push(4)
    maxStack.push(50)
    maxStack.push(32)
    maxStack.push(22)
    maxStack.push(45)
    maxStack.push(31)
    maxStack.push(52)

    maxStack.pop()
    maxStack.pop()
    maxStack.pop()

    print(maxStack.max())
}