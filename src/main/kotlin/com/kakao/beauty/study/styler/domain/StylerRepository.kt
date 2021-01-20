package com.kakao.beauty.study.styler.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StylerRepository : JpaRepository<Styler, Long> {
    fun findByShopId(shopId: Long): List<Styler>
}