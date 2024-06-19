package com.example.playground

class Archer : Character {
    var name: String
    var age: Int
    var gender: String
    var money: Int
    var hp: Int

    constructor(_name: String, _age: Int, _gender: String, _money: Int, _hp: Int) {
        name = _name
        age = _age
        gender = _gender
        money = _money
        hp = _hp
        println("${name}궁수 생성")
    }

    override fun attack() {
        println("활쏘기!")
    }

    fun windArrow() {
        println("바람의 화살!!")
    }

    fun windJump(destination: String) {
        println("${destination}까지 도약!")
    }
}