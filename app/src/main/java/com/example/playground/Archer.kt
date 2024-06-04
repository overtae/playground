package com.example.playground

class Archer {
    var name: String = ""
    var age: Int = 0
    var gender: String = ""
    var money: Int = 0
    var hp: Int = 0

    constructor(_name: String, _age: Int, _gender: String, _money: Int, _hp: Int) {
        println("${name}궁수 생성")
        name = _name
        age = _age
        gender = _gender
        money = _money
        hp = _hp
    }

    fun attack() {
        println("활쏘기!")
    }

    fun windArrow() {
        println("바람의 화살!!")
    }

    fun windJump(destination: String) {
        println("${destination}까지 도약!")
    }
}