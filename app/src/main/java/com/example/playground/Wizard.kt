package com.example.playground

class Wizard : Character {
    var name: String
    var age: Int
    var gender: String
    var money: Int
    var hp: Int
    var mp: Int

    constructor(_name: String, _age: Int, _gender: String, _money: Int, _hp: Int, _mp: Int) {
        name = _name
        age = _age
        gender = _gender
        money = _money
        hp = _hp
        mp = _mp
        println("${name}마법사 생성")
    }

    override fun attack() {
        println("에너지 볼!")
    }

    fun fireBall() {
        println("파이어 볼!")
    }

    fun teleport(src: Int, dst: Int) {
        println("${src}에서 ${dst}로 텔레포트!")
    }
}