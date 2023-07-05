package com.itis.tinkoff.ui.screens.home

import com.itis.tinkoff.domain.models.ProductModel

sealed interface HomeAction {

}

sealed interface HomeEvent {

    class OnProductClick(val product: ProductModel) : HomeEvent

    object LoadMore : HomeEvent
}
