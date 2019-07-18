package com.radix2.algorithms.extras

class ListNode(var `val`: Int, var next: ListNode? = null)

fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
    var trav1 = l1
    var trav2 = l2

    var carry = 0
    var sum: Int

    var result: ListNode? = null
    var last: ListNode? = null

    fun addLast(num: Int) {
        val node = ListNode(num)

        if (result == null) {
            result = node
            last = result
        } else {
            last?.next = node
            last = last?.next
        }
    }

    while (trav1 != null || trav2 != null) {
        sum = carry + (trav1?.`val` ?: 0) + (trav2?.`val` ?: 0)

        carry = if (sum > 9) 1 else 0

        sum %= 10

        addLast(sum)

        if (trav1 != null)
            trav1 = trav1.next

        if (trav2 != null)
            trav2 = trav2.next
    }

    if (carry == 1)
        last?.next = ListNode(carry)

    return result
}

fun main(args: Array<String>) {
    val n1 = ListNode(5)
    val n2 = ListNode(5)

    var result = addTwoNumbers(n1, n2)

    while (result != null) {
        fib(result.`val`)
        result = result.next
    }
}