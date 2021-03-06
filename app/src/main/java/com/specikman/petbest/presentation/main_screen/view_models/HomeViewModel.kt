package com.specikman.petbest.presentation.main_screen.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.*
import com.specikman.petbest.domain.use_case.cart_use_cases.add_cart.AddCartUseCase
import com.specikman.petbest.domain.use_case.cart_use_cases.delete_cart.DeleteCartUseCase
import com.specikman.petbest.domain.use_case.cart_use_cases.get_carts.GetCartsUseCase
import com.specikman.petbest.domain.use_case.cart_use_cases.update_cart.UpdateCartUseCase
import com.specikman.petbest.domain.use_case.get_services.GetServicesUseCase
import com.specikman.petbest.domain.use_case.history_use_cases.AddHistoryUseCase
import com.specikman.petbest.domain.use_case.history_use_cases.GetHistoryUseCase
import com.specikman.petbest.domain.use_case.order_use_cases.add_order.AddOrderUseCase
import com.specikman.petbest.domain.use_case.order_use_cases.get_orders.GetOrdersUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.favorite.AddFavoriteUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.favorite.GetFavoriteUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetBestSellerProductsUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetMostDiscountProductsUseCase
import com.specikman.petbest.domain.use_case.product_use_cases.get_products.GetProductsUseCase
import com.specikman.petbest.domain.use_case.user_use_cases.GetUsersUseCase
import com.specikman.petbest.domain.use_case.user_use_cases.UpdateUserUseCase
import com.specikman.petbest.presentation.main_screen.state.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getBestSellerProductsUseCase: GetBestSellerProductsUseCase,
    private val getMostDiscountProductsUseCase: GetMostDiscountProductsUseCase,
    private val getCartsUseCase: GetCartsUseCase,
    private val addCartUseCase: AddCartUseCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val getOrdersUseCase: GetOrdersUseCase,
    private val addOrderUseCase: AddOrderUseCase,
    private val getServicesUseCase: GetServicesUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val addHistoryUseCase: AddHistoryUseCase
) : ViewModel() {

    private val _stateProducts = mutableStateOf(ProductsState())
    val stateProducts: State<ProductsState> = _stateProducts

    private val _stateBestSellerProducts = mutableStateOf(ProductsState())
    val stateBestSellerProducts: State<ProductsState> = _stateBestSellerProducts

    private val _stateMostDiscountProducts = mutableStateOf(ProductsState())
    val stateMostDiscountProducts: State<ProductsState> = _stateMostDiscountProducts

    private val _stateServices = mutableStateOf(ServicesState())
    val stateServices: State<ServicesState> = _stateServices


    private val _stateCarts = mutableStateOf(CartsState())
    val stateCarts: State<CartsState> = _stateCarts

    private val _stateUpdateCart = mutableStateOf("")
    val stateUpdateCart: State<String> = _stateUpdateCart

    private val _stateFavorite = mutableStateOf(FavoriteState())
    val stateFavorite: State<FavoriteState> = _stateFavorite

    private val _stateFavoriteList = mutableStateOf(emptyList<Favorite>())
    val stateFavoriteList: State<List<Favorite>> = _stateFavoriteList

    private val _stateOrders = mutableStateOf(OrdersState())
    val stateOrders: State<OrdersState> = _stateOrders

    private val _stateUsers = mutableStateOf(UsersState())
    val stateUsers: State<UsersState> = _stateUsers

    private val _stateHistory = mutableStateOf(HistoryState())
    val stateHistory: State<HistoryState> = _stateHistory


    init {
        getProducts()
        getBestSellerProducts()
        getMostDiscountProducts()
        getCarts()
        getFavorite()
        getOrders()
        getServices()
        getUsers()
        getHistory()
    }

    private fun getProducts() {
        getProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateProducts.value = ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateProducts.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateProducts.value =
                        ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getBestSellerProducts() {
        getBestSellerProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateBestSellerProducts.value =
                        ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateBestSellerProducts.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateBestSellerProducts.value =
                        ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }


    private fun getMostDiscountProducts() {
        getMostDiscountProductsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateMostDiscountProducts.value =
                        ProductsState(products = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateMostDiscountProducts.value = ProductsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateMostDiscountProducts.value =
                        ProductsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }


    //Cart
    private fun getCarts() {
        getCartsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateCarts.value = CartsState(carts = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateCarts.value = CartsState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateCarts.value =
                        CartsState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addCart(cart: Cart) {
        addCartUseCase(cart).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateCart(cart: Cart) {
        updateCartUseCase(cart).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun deleteCart(cart: Cart) {
        deleteCartUseCase(cart).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addFavorite(favorite: Favorite) = addFavoriteUseCase(favorite).onEach { result ->
        when (result) {
            is Resource.Success -> {
                _stateFavorite.value = FavoriteState(isLike = result.data ?: false)
            }
            is Resource.Loading -> {
                _stateFavorite.value = FavoriteState(isLoading = true)
            }
            is Resource.Error -> {
                _stateFavorite.value = FavoriteState(error = "Error")
            }
        }
    }.launchIn(viewModelScope)


    fun getFavorite() {
        getFavoriteUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateFavoriteList.value = result.data ?: emptyList<Favorite>()
                }
                is Resource.Loading -> {
                    _stateFavoriteList.value = emptyList<Favorite>()
                }
                is Resource.Error -> {
                    _stateFavoriteList.value =
                        emptyList<Favorite>()
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getOrders() {
        getOrdersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateOrders.value = OrdersState(orders = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateOrders.value = OrdersState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateOrders.value =
                        OrdersState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addOrder(order: Order){
        addOrderUseCase(order = order).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getServices() {
        getServicesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateServices.value = ServicesState(services = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateServices.value = ServicesState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateServices.value =
                        ServicesState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getUsers() {
        getUsersUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateUsers.value = UsersState(users = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateUsers.value = UsersState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateUsers.value =
                        UsersState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateUser(user: User){
        updateUserUseCase(user = user).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getHistory() {
        getHistoryUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _stateHistory.value = HistoryState(history = result.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _stateHistory.value = HistoryState(isLoading = true)
                }
                is Resource.Error -> {
                    _stateHistory.value =
                        HistoryState(error = result.message ?: "An unexpected error occurred")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addHistory(history: History){
        addHistoryUseCase(history = history).onEach { result ->
            when (result) {
                is Resource.Success -> {
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }.launchIn(viewModelScope)
    }
}

