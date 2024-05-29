package com.example.playground

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import java.util.ArrayList

class TestActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    val questionnaireResults = QuestionnaireResults()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = ViewPagerAdapter(this)
        // 사용자가 넘길 수 있는가?
        viewPager.isUserInputEnabled = false
    }

    // 다음 페이지로 넘기는 함수
    fun moveToNextQuestion() {
        if (viewPager.currentItem == 3) {
            // 마지막 페이지 -> 결과 화면으로 이동
            val intent = Intent(this, ResultActivity::class.java)
            intent.putIntegerArrayListExtra("results", ArrayList(questionnaireResults.results))
            startActivity(intent)
        } else {
            val nextItem = viewPager.currentItem + 1

            if (nextItem < viewPager.adapter?.itemCount ?: 0) {
                // 페이지가 넘어갈 때 부드럽게 이동할 것인가
                viewPager.setCurrentItem(nextItem, true)
            }
        }
    }
}

class QuestionnaireResults {
    val results = mutableListOf<Int>()

    fun addResponse(response: List<Int>) {
        // 각 숫자 별로 그룹을 나눈다. (1과 2의 그룹)
        // 각 그룹의 개수를 센다.
        // 개수가 더 많은 그룹의 키 값을 구한다. (키 값은 1 또는 2)
        val mostFrequent = response.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        mostFrequent?.let { results.add(it) }
    }
}