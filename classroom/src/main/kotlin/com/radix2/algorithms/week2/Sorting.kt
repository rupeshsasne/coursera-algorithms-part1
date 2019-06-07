package com.radix2.algorithms.week2

fun IntArray.selectionSort() {
    for (i in 0 until size) {
        var j = i
        var minIndex = j

        while (j < size) {
            if (this[minIndex] > this[j]) {
                minIndex = j
            }

            j++
        }

        val temp = this[i]
        this[i] = this[minIndex]
        this[minIndex] = temp
    }
}

fun IntArray.insertionSort() {
    for (i in 0 until this.size) {
        var j = i

        while (j > 0) {
            if (this[j] < this[j - 1]) {
                val temp = this[j - 1]
                this[j - 1] = this[j]
                this[j] = temp
            } else {
                break
            }

            j--
        }
    }
}

enum class Color(val id: Int) {
    BLUE(0), WHITE(1), RED(2);
}

data class Pebble(val color: Color)

fun dutchNationalFlag(array: Array<Pebble>) {
    var left = 0
    var mid = 0
    var right = array.size - 1

    fun swap(i: Int, j: Int) {
        val temp = array[i]
        array[i] = array[j]
        array[j] = temp
    }

    while (mid <= right) {
        when {
            array[mid].color == Color.BLUE -> swap(left++, mid++)
            array[mid].color === Color.RED -> swap(mid, right--)
            array[mid].color == Color.WHITE -> mid++
        }
    }
}

fun main(args: Array<String>) {
    val array = arrayOf(Pebble(Color.WHITE), Pebble(Color.RED), Pebble(Color.BLUE), Pebble(Color.RED), Pebble(Color.WHITE), Pebble(Color.BLUE))

    dutchNationalFlag(array)

    val str = array.joinToString { pebble ->
        pebble.color.toString()
    }

    println(str)
}