package com.example.playground

class Slime : Monster, GreenSlimeSkill {
    var name: String = ""
    var color: String = ""
    var height: Double = 0.0
    var hp: Int = 0
    var damage: Int = 0

    constructor(_name: String, _color: String, _height: Double, _hp: Int, _damage: Int) {
        name = _name
        color = _color
        height = _height
        hp = _hp
        damage = _damage
    }

    override fun attack() {
        println("점성 공격!")
    }

    fun jumpAttack() {
        println("점프해서 내려찍기!")
    }

    override fun poison() {
        if (color == "초록") {
            println("초록 독 퍼뜨리기!")
        } else {
            println("일반 슬라임은 사용할 수 없습니다.")
        }
    }
}