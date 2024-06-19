package com.example.playground

class Zombie : Monster {
    var name: String
    var color: String
    var height: Double
    var hp: Int
    var damage: Int

    constructor(_name: String, _color: String, _height: Double, _hp: Int, _damage: Int) {
//        println("${name}좀비 생성")
        name = _name
        color = _color
        height = _height
        hp = _hp
        damage = _damage
        println("${name}좀비 생성")
    }

    override fun attack() {
        println("물어뜯기 공격!")
    }

    fun virus() {
        println("바이러스 퍼뜨리기!")
    }
}