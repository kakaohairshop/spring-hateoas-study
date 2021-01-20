package com.kakao.beauty.study.shop.domain

import com.kakao.beauty.study.styler.domain.Styler
import javax.persistence.*

@Entity
class Shop(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,

        val name: String
) {
    fun modifyName(name: String): Shop {
        return Shop(this.id, name)
    }

    @OneToMany
    @JoinColumn(name = "shop_id")
    private val stylers: Set<Styler> = mutableSetOf()
}