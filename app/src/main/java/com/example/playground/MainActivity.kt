package com.example.playground

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.playground.data.DustItem
import com.example.playground.databinding.ActivityMainBinding
import com.example.playground.retrofit.NetWorkClient
import com.skydoves.powerspinner.IconSpinnerAdapter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var items = mutableListOf<DustItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 화면 왼쪽 도시 선택
        binding.spinnerViewSido.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            // 선택한 도시로 setUpDustParameter 호출 (요청 파라미터 생성)
            communicateNetWork(setUpDustParameter(text))
        }

        // 화면 오른쪽 지역 선택
        binding.spinnerViewGoo.setOnSpinnerItemSelectedListener<String> { _, _, _, text ->
            // 선택한 지역 이름을 기준으로 item을 필터링한다.
            Log.d("miseya", "selectedItem: spinnerViewGoo selected >  $text")
            val selectedItem = items.filter { f -> f.stationName == text }
            Log.d("miseya", "selectedItem: sidoName > " + selectedItem[0].sidoName)
            Log.d("miseya", "selectedItem: pm10Value > " + selectedItem[0].pm10Value)

            // 같은 지역에 대한 값이 여러 개일 수 있다. (이전 것들까지 포함 되어 있음)
            // 가장 최근의 측정 값을 표시해주기 위해 0번째 값을 사용한다.
            binding.tvCityname.text = selectedItem[0].sidoName + "  " + selectedItem[0].stationName
            binding.tvDate.text = selectedItem[0].dataTime
            binding.tvP10value.text = selectedItem[0].pm10Value + " ㎍/㎥"

            // 등급에 따라 이미지와 색상 변경
            when (getGrade(selectedItem[0].pm10Value)) {
                1 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#9ED2EC"))
                    binding.ivFace.setImageResource(R.drawable.mise1)
                    binding.tvP10grade.text = "좋음"
                }

                2 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#D6A478"))
                    binding.ivFace.setImageResource(R.drawable.mise2)
                    binding.tvP10grade.text = "보통"
                }

                3 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#DF7766"))
                    binding.ivFace.setImageResource(R.drawable.mise3)
                    binding.tvP10grade.text = "나쁨"
                }

                4 -> {
                    binding.mainBg.setBackgroundColor(Color.parseColor("#BB3320"))
                    binding.ivFace.setImageResource(R.drawable.mise4)
                    binding.tvP10grade.text = "매우나쁨"
                }
            }
        }
    }

    // http 통신(혹은 데이터베이스 작업)은 메인 스레드에서 하면 안된다.
    // 메인 스레드에서 하게 될 경우 통신을 하는 동안 앱이 멈추게 되기 때문이다.
    // 백그라운드에서 작업 하자 : coroutine을 사용해 별도의 스레드 위에서 동작하게끔 한다.
    private fun communicateNetWork(param: HashMap<String, String>) = lifecycleScope.launch() {
        // getDust 실행
        // retrofit 실행 ... -> responseData가 정의해놓은 DTO 클래스 상태로 들어온다.
        val responseData = NetWorkClient.dustNetWork.getDust(param)
        Log.d("Parsing Dust ::", responseData.toString())

        val adapter = IconSpinnerAdapter(binding.spinnerViewGoo)
        // DTO 클래스 형태로 들어온 responseData 중, dustItem 값이 필요 (list 형태)
        items = responseData.response.dustBody.dustItem!!

        val goo = ArrayList<String>()
        items.forEach {
            // 아이템(리스트)에서, 지역명(stationName)을 꺼내 goo 리스트에 넣는다.
            Log.d("add Item :", it.stationName)
            goo.add(it.stationName)
        }

        // coroutine의 별도 스레드에서는 ui 스레드에 접근이 불가하다.
        // ui 레이아웃을 건드리기 위해서는 ui 스레드를 실행시킬 것이라고 지정을 해줘야 한다.
        // 따라서 runOnUiThread 사용.
        runOnUiThread {
            // 오른쪽 스피너에 아이템을 넣는다.
            binding.spinnerViewGoo.setItems(goo)
        }
    }

    // 요청 파라미터 생성
    // HashMap 반환
    private fun setUpDustParameter(sido: String): HashMap<String, String> {
        val authKey = BuildConfig.DUST_API_KEY

        return hashMapOf(
            "serviceKey" to authKey,
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to sido,
            "ver" to "1.0"
        )
    }

    private fun getGrade(value: String): Int {
        val mValue = value.toInt()
        var grade = 1
        grade = if (mValue in 0..30) {
            1
        } else if (mValue in 31..80) {
            2
        } else if (mValue in 81..100) {
            3
        } else 4
        return grade
    }
}