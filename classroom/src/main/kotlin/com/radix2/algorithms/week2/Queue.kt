package com.radix2.algorithms.week2

import java.lang.RuntimeException

class Queue<T> {
    var array = Array<Any?>(10) { null }

    var head: Int = -1
    var tail: Int = -1

    fun enqueue(item: T) {
        array[++tail] = item

        if (tail == 0)
            head = 0
    }

    fun dequeue(): T {

        if (isEmpty())
            throw EmptyQueueException()

        @Suppress("UNCHECKED_CAST")
        return array[head++] as T
    }

    fun isEmpty() = head == tail

    private fun resize(capacity: Int) {
        val tempArray = Array<Any?>(capacity) { null }

        System.arraycopy(array, head, tempArray, 0, tail - head)

        array = tempArray
    }

    class EmptyQueueException : RuntimeException()
}

fun main(args: Array<String>) {
    val q = Queue<Int>()

    q.enqueue(1)
    q.enqueue(2)
    q.enqueue(3)
    q.enqueue(4)
    q.enqueue(5)

    println(q.dequeue())
    println(q.dequeue())
    println(q.dequeue())
    println(q.dequeue())
    println(q.dequeue())

    println(q.dequeue())
}

