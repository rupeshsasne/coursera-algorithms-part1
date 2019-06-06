package com.radix2.algorithms.week2

import java.util.*

class Stack<T> {
    var array: Array<Any?> = arrayOfNulls(1)

    var top = -1

    fun push(item: T) {

        if (top == array.size - 1)
            resize(2 * array.size)

        array[++top] = item
    }

    fun pop(): T {
        if (isEmpty())
            throw EmptyStackException()

        @Suppress("UNCHECKED_CAST")
        val value =  array[top--] as T

        if (top > -1 && top == (array.size - 1) / 4)
            resize(array.size / 2)

        return value
    }

    fun isEmpty() = top == -1

    private fun resize(capacity: Int) {
        val tempArray = Array<Any?>(capacity) { null }

        System.arraycopy(array, 0, tempArray, 0, minOf(array.size, capacity))

        array = tempArray
    }
}

fun main(args: Array<String>) {
    val stack = Stack<Int>()

    for (i in 0..100000) {
        stack.push(i)
    }

    for (i in 0..5000)
        stack.pop()

    println(stack.pop())
}