package com.radix2.algorithms.week4

class Node(var data: Int, var left: Node? = null, var right: Node? = null)

fun checkBST(node: Node?, min: Int, max: Int): Boolean {
    if (node == null)
        return true

    if (node.data <= min || node.data >= max) {
        return false
    }

    return checkBST(node.left, min, node.data) && checkBST(node.right, node.data, max)
}