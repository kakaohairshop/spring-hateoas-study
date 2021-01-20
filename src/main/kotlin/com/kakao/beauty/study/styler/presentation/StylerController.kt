package com.kakao.beauty.study.styler.presentation

import com.kakao.beauty.study.shop.domain.ShopRepository
import com.kakao.beauty.study.styler.domain.Styler
import com.kakao.beauty.study.styler.domain.StylerRepository
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.MediaTypes
import org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("v1/shops/{shopId}/stylers", produces = [MediaTypes.HAL_JSON_VALUE])
class StylerController(val shopRepository: ShopRepository, val stylerRepository: StylerRepository) {


    @PostMapping
    fun createStylers(@PathVariable shopId: Long, @RequestBody stylerRequest: StylerRequest): EntityModel<Styler> {
        val shop = shopRepository.findById(shopId)
                .orElseThrow { IllegalArgumentException("잘못된 shopID 입니다.") }

        val styler = stylerRepository.save(Styler(name = stylerRequest.name, shop = shop))

        return EntityModel.of(
                styler,
                linkTo(methodOn(StylerController::class.java).findStyler(shopId, styler.id)).withSelfRel(),
                linkTo(methodOn(StylerController::class.java).findStyler(shopId, styler.id)).withRel("query-event"),
                linkTo(methodOn(StylerController::class.java)).withRel("update-event"),
                linkTo(methodOn(StylerController::class.java)).withRel("delete-event")
        )
    }

    @GetMapping
    fun findStylers(@PathVariable shopId: Long): CollectionModel<EntityModel<Styler>> {
        val stylers = stylerRepository.findByShopId(shopId)
                .map {
                    EntityModel.of(it,
                            linkTo(methodOn(StylerController::class.java).findStyler(it.shop.id, it.id)).withSelfRel(),
                            linkTo(methodOn(StylerController::class.java).findStyler(shopId, it.id)).withRel("query-event"),
                            linkTo(methodOn(StylerController::class.java)).withRel("update-event"),
                            linkTo(methodOn(StylerController::class.java)).withRel("delete-event")
                    )
                }
                .toList()

        return CollectionModel.of(stylers,
                linkTo(methodOn(StylerController::class.java).findStylers(shopId)).withRel("self")
        )
    }

    @GetMapping("/{stylerId}")
    fun findStyler(@PathVariable shopId: Long, @PathVariable stylerId: Long): EntityModel<Styler> {
        val styler = stylerRepository.findById(stylerId)
                .orElseThrow { IllegalArgumentException("잘못된 stylerId 입니다.") }

        return EntityModel.of(styler,
                linkTo(methodOn(StylerController::class.java).findStyler(shopId, stylerId)).withRel("self"),
                linkTo(methodOn(StylerController::class.java).findStyler(shopId, stylerId)).withRel("query-event"),
                linkTo(methodOn(StylerController::class.java)).withRel("update-event"),
                linkTo(methodOn(StylerController::class.java)).withRel("delete-event")
        )
    }

    @PutMapping("/{stylerId}")
    fun updateStyler(@PathVariable shopId: Long, @PathVariable stylerId: Long, @RequestBody stylerRequest: StylerRequest): EntityModel<Styler> {
        val styler = stylerRepository.findById(stylerId)
                .orElseThrow { IllegalArgumentException("잘못된 stylerId 입니다.") }

        val updatedStyler = stylerRepository.save(styler.modifyName(stylerRequest.name))

        return EntityModel.of(updatedStyler,
                linkTo(methodOn(StylerController::class.java).findStyler(shopId, stylerId)).withRel("self"),
                linkTo(methodOn(StylerController::class.java).findStyler(shopId, stylerId)).withRel("query-event"),
                linkTo(methodOn(StylerController::class.java)).withRel("update-event"),
                linkTo(methodOn(StylerController::class.java)).withRel("delete-event")
        )
    }

    @DeleteMapping("/{stylerId}")
    fun deleteStyler(@PathVariable shopId: Long, @PathVariable stylerId: Long) {
        stylerRepository.findById(stylerId)
                .orElseThrow { IllegalArgumentException("잘못된 shopId 입니다.") }

        stylerRepository.deleteById(stylerId)
    }
}