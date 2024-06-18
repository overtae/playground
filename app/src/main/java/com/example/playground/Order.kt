package com.example.playground

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.round

class Order(private val cart: ArrayList<Cart>) {
    private var purchasedDate: LocalDateTime = LocalDateTime.now()
    private var total: Double = cart.sumOf { it.subMenu.price }

    companion object {
        val timeFormmatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("a HH시 mm분").withLocale(Locale.KOREA)
        val datetimeFormmatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd일 HH:mm:ss").withLocale(Locale.KOREA)

        private val noPurchaseTimeFrom = LocalTime.of(1, 15)
        private val noPurchaseTimeTo = LocalTime.of(23, 1)
    }

    fun displayInfo() {
        val formattedPurchasedDate = purchasedDate.format(datetimeFormmatter)
        val firstMenu = cart.first().subMenu.name
        val menuSize = cart.size
        val menuInfo = firstMenu + if (menuSize > 1) " 외 $menuSize 개" else ""

        println("$formattedPurchasedDate | W ${"$total".padStart(5, ' ')} | $menuInfo")
    }

    fun purchase(money: Double): Boolean {
        val currentTime = purchasedDate.format(timeFormmatter)
        val currentDate = purchasedDate.format(datetimeFormmatter)

        if (total <= money) {
            if ((purchasedDate.toLocalTime()) in noPurchaseTimeFrom..noPurchaseTimeTo) {
                println("\n현재 시각은 ${currentTime}입니다.")
                println(
                    "은행 점검 시간은 ${noPurchaseTimeFrom.format(timeFormmatter)} ~ ${
                        noPurchaseTimeTo.format(
                            timeFormmatter
                        )
                    }이므로 결제할 수 없습니다.\n"
                )
                return false
            }

            println("결제를 완료했습니다. ($currentDate)")
            println("현재 잔액은 ${money}W 입니다.\n")

            return true
        } else {
            val diff = round((total - money) * 10) / 10
            println("현재 잔액은 ${money}W 으로 ${diff}W이 부족해서 주문할 수 없습니다.")

            return false
        }
    }
}