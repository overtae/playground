package com.example.playground.retrofit

import com.example.playground.data.Dust
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetWorkInterface {
    // 시도별 실시간 측정 정보 조회 주소
    // HaspMap 형태로 요청 값들이 들어간다. ex. serviceKey, numOfRows, pageNo, ...
    @GET("getCtprvnRltmMesureDnsty")
    suspend fun getDust(@QueryMap param: HashMap<String, String>): Dust
}