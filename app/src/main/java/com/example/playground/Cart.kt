package com.example.playground

class Cart(val subMenu: SubMenu) {
    var id: Int

    init {
        this.id = getNextId()
    }

    companion object {
        private var maxId = 1

        private fun getNextId(): Int {
            return maxId++
        }
    }
}