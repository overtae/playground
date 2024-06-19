package com.example.playground

fun main() {
    val worldName = "스코월드"

    var myName = inputMyInfo("name").toString()

    var myAge = inputMyInfo("age").toString().toInt()
    print(myAge)

    var myJob = inputMyInfo("job").toString()

    var myGender = inputMyInfo("gender").toString()

    var myMoney = inputMyInfo("money").toString().toInt()

    var myHp = inputMyInfo("hp").toString().toInt()

    var isNamePass = true
    var isAgePass = true
    var isJobPass = true

    var names = arrayOf("참새", "꿩", "비둘기")
    for (name in names) {
        if (myName == name) {
            println("중복된 이름이 존재합니다.")
            isNamePass = false
            break
        }
    }

    if (myAge < 12) {
        println("12세 미만은 이용할 수 없습니다.")
        isAgePass = false
    }
    if (myJob == "전사") {
        println("일시적으로 전사를 이용할 수 없습니다.")
        isJobPass = false
    }

    // 모든 조건을 통과한 경우에만 환영
    if (isNamePass && isAgePass && isJobPass) {
        displayInfo(worldName, myName, myAge, myJob)

        if (myJob == "마법사") {
            println("마법사는 초기 mp도 입력해주세요")
            var myMp = inputMyInfo("mp").toString().toInt()
            var myCharacter = Wizard(myName, myAge, myGender, myMoney, myHp, myMp)

            println("던전을 선택해주세요")
            println("[1] 슬라임동굴, [2] 좀비마을")
            var selectWorld = inputMyInfo("selectWorld").toString().toInt()
            selectWorldByWizard(selectWorld, myCharacter)

        } else if (myJob == "궁수") {
            println("궁수를 선택했군요")
            var myCharacter = Archer(myName, myAge, myGender, myMoney, myHp)

            println("던전을 선택해주세요")
            println("[1] 슬라임동굴, [2] 좀비마을")
            var selectWorld = inputMyInfo("selectWorld").toString().toInt()
            selectWorldByArcher(selectWorld, myCharacter)
        }
    }
}

fun displayInfo(worldName: String, myName: String, myAge: Int, myJob: String) {
    println("==================${worldName}에 오신것을 환영합니다==================")
    println("당신의 정보는 다음과 같습니다.")
    println("이름: ${myName}입니다.")
    println("나이: ${myAge}입니다.")
    println("직업: ${myJob}입니다.")
    println("모험을 떠나 볼까요?")
}

fun selectWorldByArcher(selectWorld: Int, myCharacter: Archer) {
    if (selectWorld == 1) { // 슬라임 던전
        var slime1 = Slime("초록슬라임", "초록", 30.2, 200, 10)
        slime1.attack()
        myCharacter.windArrow()

        slime1.poison()

    } else if (selectWorld == 2) { // 좀비 던전
        var zombie1 = Zombie("파랑좀비", "파랑", 142.2, 500, 25)
        zombie1.virus()
        myCharacter.windJump("건물1")
    }
}

fun selectWorldByWizard(selectWorld: Int, myCharacter: Wizard) {
    if (selectWorld == 1) { // 슬라임 던전
        var slime1 = Slime("파랑슬라임", "파랑", 30.2, 200, 10)
        slime1.attack()
        myCharacter.attack()

        slime1.poison()

    } else if (selectWorld == 2) { // 좀비 던전
        var zombie1 = Zombie("파랑좀비", "파랑", 142.2, 500, 25)
        zombie1.virus()
        myCharacter.fireBall()
    }
}

fun inputMyInfo(type: String): Any? {
    return when (type) {
        "name" -> {
            println("이름을 입력해주세요")
            while (true) {
                try {
                    var originName = readLine()
                    if (originName?.first() != '_' && originName?.first() != '!') {
                        return originName
                    } else {
                        println("이름을 다시 입력해주세요")
                    }
                } catch (e: Exception) {
                    println("이름을 다시 입력해주세요")
                }
            }
        }

        "age" -> {
            println("나이를 입력해주세요")
            while (true) {
                try {
                    var originAge: String? = readLine()
                    return originAge?.toInt() ?: -1
                } catch (e: Exception) {
                    println("나이를 다시 입력해주세요")
                }
            }
        }

        "job" -> {
            println("직업을 입력해주세요")
            while (true) {
                try {
                    var originName = readLine()
                    if (originName?.equals("궁수") == true || originName?.equals("마법사") == true) {
                        return originName
                    } else {
                        println("직업을 다시 입력해주세요")
                    }
                } catch (e: Exception) {
                    println("직업을 다시 입력해주세요")
                }
            }
        }

        "gender" -> {
            println("성별을 입력해주세요")
            while (true) {
                try {
                    var originGender = readLine()
                    if (originGender?.equals("남") == true || originGender?.equals("여") == true) {
                        return originGender
                    } else {
                        println("성별을 다시 입력해주세요")
                    }
                } catch (e: Exception) {
                    println("성별을 다시 입력해주세요")
                }
            }
        }

        "money" -> {
            println("초기자본을 입력해주세요")
            while (true) {
                try {
                    var originMoney: String? = readLine()
                    return originMoney?.toInt() ?: -1
                } catch (e: Exception) {
                    println("초기자본을 다시 입력해주세요")
                }
            }
        }

        "hp" -> {
            println("초기체력을 입력해주세요")
            while (true) {
                try {
                    var originHp: String? = readLine()
                    return originHp?.toInt() ?: -1
                } catch (e: Exception) {
                    println("초기체력을 다시 입력해주세요")
                }
            }
        }

        "mp" -> {
            println("초기마나를 입력해주세요")
            while (true) {
                try {
                    var originMp: String? = readLine()
                    return originMp?.toInt() ?: -1
                } catch (e: Exception) {
                    println("초기마나를 다시 입력해주세요")
                }
            }
        }

        "selectWorld" -> {
            println("월드를 선택해주세요")
            while (true) {
                try {
                    var selectWorld: String? = readLine()
                    return selectWorld?.toInt() ?: -1
                } catch (e: Exception) {
                    println("월드를 다시 선택해주세요")
                }
            }
        }

        else -> {
            return "no"
        }
    }
}