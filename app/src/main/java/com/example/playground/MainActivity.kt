package com.example.playground

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    // 화면 요소들 정의: 사용자에게 이벤트를 받을 것이다.
    private val clearButton by lazy { findViewById<Button>(R.id.btn_clear) }
    private val addButton by lazy { findViewById<Button>(R.id.btn_add) }
    private val runButton by lazy { findViewById<Button>(R.id.btn_run) }
    private val numPick by lazy { findViewById<NumberPicker>(R.id.np_num) }

    // 로또 번호 (6개의 공) 정의
    private val numTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.tv_num1),
            findViewById(R.id.tv_num2),
            findViewById(R.id.tv_num3),
            findViewById(R.id.tv_num4),
            findViewById(R.id.tv_num5),
            findViewById(R.id.tv_num6),
        )
    }

    // 현재 run 상태인지 판단
    // true: 번호가 꽉 차있는 상태, 번호 추가 불가
    // false: 번호 추가 가능
    private var didRun = false

    // 사용자가 지정한 숫자를 담아둘 공간 정의
    private val pickNumberSet = hashSetOf<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 넘버 피커의 최대, 최소를 지정
        numPick.minValue = 1
        numPick.maxValue = 45

        // 세 가지의 초기화
        initAddButton()
        initRunButton()
        initClearButton()
    }

    // 사용자 지정 번호 추가
    // 조건 1. 번호가 꽉 차 있으면 추가 불가
    // 조건 2. 사용자 지정 번호는 5개까지만 가능
    // 조건 3. 중복 번호 추가 불가
    private fun initAddButton() {
        addButton.setOnClickListener {
            when {
                didRun -> showToast("초기화 후에 시도해주세요.")
                pickNumberSet.size >= 6 -> showToast("숫자는 최대 5개까지 선택할 수 있습니다.")
                pickNumberSet.contains(numPick.value) -> showToast("이미 선택된 숫자입니다.")
                else -> {
                    // pickNumberSet에는 사용자가 선택했던 숫자들이 담기고,
                    // 그 길이에 해당하는 공을 보여준다.
                    val textView = numTextViewList[pickNumberSet.size]
                    textView.isVisible = true
                    textView.text = numPick.value.toString()

                    setNumBack(numPick.value, textView)
                    pickNumberSet.add(numPick.value)
                }
            }
        }
    }

    // 번호 초기화
    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numTextViewList.forEach { it.isVisible = false }
            didRun = false
            numPick.value = 1
        }
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandom()

            didRun = true

            // 공을 만들어준다.
            list.forEachIndexed { index, number ->
                val textView = numTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setNumBack(number, textView)
            }
        }
    }

    private fun getRandom(): List<Int> {
        // 사용자가 지정한 번호를 제외한 숫자
        val numbers = (1..45).filter { it !in pickNumberSet }
        // numbers를 섞은 후, 6개에서 사용자가 지정한 숫자의 개수를 뺀 만큼 가져온 후,
        // pickNumberSet과 더해준다.
        // 그리고, 오름차순으로 정렬한다.
        return (pickNumberSet + numbers.shuffled().take(6 - pickNumberSet.size)).sorted()
    }

    // 원 모양의 텍스트 뷰의 배경을 설정
    private fun setNumBack(number: Int, textView: TextView) {
        val background = when (number) {
            in 1..10 -> R.drawable.circle_yellow
            in 11..20 -> R.drawable.circle_blue
            in 21..30 -> R.drawable.circle_red
            in 31..40 -> R.drawable.circle_gray
            else -> R.drawable.circle_green
        }
        println("!!! $background !!!")
        textView.background = ContextCompat.getDrawable(this, background)
    }

    // 토스트 메시지
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}