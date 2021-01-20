package com.kakao.beauty.study.shop.presentation

import com.kakao.beauty.study.shop.domain.Shop
import com.kakao.beauty.study.styler.presentation.StylerController
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.server.RepresentationModelAssembler
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.stereotype.Component

@Component
class ShopResourceAssembler : RepresentationModelAssembler<Shop, EntityModel<Shop>> {
    override fun toModel(shop: Shop): EntityModel<Shop> {
        return EntityModel.of(shop,
                linkTo(methodOn(ShopController::class.java).findShop(shop.id)).withSelfRel(),
                linkTo(methodOn(StylerController::class.java).findStylers(shop.id)).withRel("stylers"),
                linkTo(methodOn(ShopController::class.java).findShop(shop.id)).withRel("query-event"),
                linkTo(methodOn(ShopController::class.java)).withRel("update-event"),
                linkTo(methodOn(ShopController::class.java)).withRel("delete-event")
        )
    }

    override fun toCollectionModel(shops: MutableIterable<Shop>): CollectionModel<EntityModel<Shop>> {
        return super.toCollectionModel(shops)
                .add(linkTo(methodOn(ShopController::class.java).findShops()).withRel("self"))
    }
}