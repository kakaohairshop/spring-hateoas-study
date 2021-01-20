package com.kakao.beauty.study.shop.presentation

import com.kakao.beauty.study.shop.domain.Shop
import com.kakao.beauty.study.shop.domain.ShopRepository
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/shops", produces = [MediaTypes.HAL_JSON_VALUE])
class ShopController(
        val shopResourceAssembler: ShopResourceAssembler,
        val shopRepository: ShopRepository) {

    @PostMapping
    fun createShop(@RequestBody shopRequest: ShopRequest): EntityModel<Shop> {
        val shop = shopRepository.save(Shop(name = shopRequest.name))

        return shopResourceAssembler.toModel(shop)
    }

    @GetMapping
    fun findShops(): CollectionModel<EntityModel<Shop>> {
        val shops = shopRepository.findAll()

        return shopResourceAssembler.toCollectionModel(shops)
    }

    @GetMapping("/{shopId}")
    fun findShop(@PathVariable shopId: Long): EntityModel<Shop> {
        val shop = shopRepository.findById(shopId)
                .orElseThrow { IllegalArgumentException("잘못된 shopID 입니다.") }

        return shopResourceAssembler.toModel(shop)
    }

    @PutMapping("/{shopId}")
    fun updateShop(@PathVariable shopId: Long, @RequestBody shopRequest: ShopRequest): EntityModel<Shop> {
        val shop = shopRepository.findById(shopId)
                .orElseThrow { IllegalArgumentException("잘못된 shopId 입니다.") }

        val updatedShop = shopRepository.save(shop.modifyName(shopRequest.name))

        return shopResourceAssembler.toModel(updatedShop)
    }

    @DeleteMapping("/{shopId}")
    fun deleteShop(@PathVariable shopId: Long) {
        val shop = shopRepository.findById(shopId)
                .orElseThrow { IllegalArgumentException("잘못된 shopId 입니다.") }

        shopRepository.deleteById(shop.id)
    }
}