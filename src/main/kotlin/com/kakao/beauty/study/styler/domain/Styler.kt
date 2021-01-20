package com.kakao.beauty.study.styler.domain

import com.kakao.beauty.study.shop.domain.Shop
import javax.persistence.*

@Entity
class Styler(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L,

        val name: String,

        @ManyToOne val shop: Shop
) {
    fun modifyName(name: String): Styler {
        return Styler(id, name, shop)
    }

}