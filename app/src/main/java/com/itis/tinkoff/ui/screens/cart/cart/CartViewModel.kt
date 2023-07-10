package com.itis.tinkoff.ui.screens.cart.cart

import androidx.lifecycle.viewModelScope
import com.itis.android.ui.base.BaseViewModel
import com.itis.tinkoff.domain.models.CartProductModel
import com.itis.tinkoff.domain.usecases.AddToCartUseCase
import com.itis.tinkoff.domain.usecases.GetCartUseCase
import com.itis.tinkoff.domain.usecases.RemoveAllFromCartUseCase
import com.itis.tinkoff.domain.usecases.RemoveFromCartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val removeFromCartUseCase: RemoveFromCartUseCase,
    private val removeAllFromCartUseCase: RemoveAllFromCartUseCase,
) : BaseViewModel<CartState, CartAction, CartEvent>(CartState()) {

    init {
        event(CartEvent.GetProducts)
    }

    override fun event(event: CartEvent) {
        when (event) {
            is CartEvent.DecreaseProductAmount -> {
                viewModelScope.launch {
                    removeFromCartUseCase(event.productId)
                        .onSuccess {
                            state {
                                copy(
                                    cartProducts = cartProducts
                                        .setNewQuantity(event.productId, it)
                                )
                            }
                        }
                        .onFailure { }
                }
            }

            is CartEvent.IncreaseProductAmount -> {
                viewModelScope.launch {
                    addToCartUseCase(event.productId)
                        .onSuccess {
                            state {
                                copy(
                                    cartProducts = cartProducts
                                        .setNewQuantity(event.productId, it)
                                )
                            }
                        }
                        .onFailure { }
                }
            }

            is CartEvent.RemoveProduct -> {
                viewModelScope.launch {
                    removeAllFromCartUseCase(event.productId)
                        .onSuccess {
                            state {
                                copy(cartProducts = cartProducts.removeProduct(event.productId))
                            }
                        }
                        .onFailure { }
                }
            }

            CartEvent.GetProducts -> {
                viewModelScope.launch {
                    state { copy(isLoading = true) }
                    getCartUseCase()
                        .onSuccess { state { copy(isLoading = false, cartProducts = it) } }
                        .onFailure { state { copy(isLoading = false) } }
                }
            }

            CartEvent.PlaceOrder -> {

            }
        }
    }

    private fun List<CartProductModel>.setNewQuantity(productId: Int, newQuantity: Int) =
        toMutableList().apply {
            val index = indexOfFirst { it.id == productId }
            set(index, get(index).run {
                copy(quantity = newQuantity)
            })
        }

    private fun List<CartProductModel>.removeProduct(productId: Int) =
        toMutableList().apply {
            removeIf { it.id == productId }
        }
}
