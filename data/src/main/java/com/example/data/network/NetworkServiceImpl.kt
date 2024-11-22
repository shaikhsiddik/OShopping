package com.example.data.network

import com.example.data.model.DataProductModel
import com.example.data.model.request.AddToCartRequest
import com.example.data.model.request.AddressDataModel
import com.example.data.model.response.CartResponse
import com.example.data.model.response.CartSummaryResponse
import com.example.data.model.response.CategoriesListResponse
import com.example.data.model.response.OrdersListResponse
import com.example.data.model.response.PlaceOrderResponse
import com.example.data.model.response.ProductListResponse
import com.example.domain.model.AddressDomainModel
import com.example.domain.model.CartItemModel
import com.example.domain.model.CartModel
import com.example.domain.model.CartSummary
import com.example.domain.model.CategoryListModel
import com.example.domain.model.OrdersListModel
import com.example.domain.model.Product
import com.example.domain.model.ProductListModel
import com.example.domain.model.request.AddCartRequestModel
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.util.InternalAPI


class NetworkServiceImpl(val client: HttpClient): NetworkService {

    private val baseUrl = "https://ecommerce-ktor-4641e7ff1b63.herokuapp.com"

    override suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel> {

        val url = if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products"

        return makeWebRequest(
            url = Url(url),
            method = HttpMethod.Get,
            mapper = { dataModule: ProductListResponse ->
                dataModule.toProductList()
            }
        )
    }

    override suspend fun getCategory(): ResultWrapper<CategoryListModel> {
        val url = "$baseUrl/categories"

        return makeWebRequest(
            url = Url(url),
            method = HttpMethod.Get,
            mapper = { categoryModule: CategoriesListResponse ->

                categoryModule.toCategoriesList()
            }
        )
    }

    override suspend fun addProductToCart(addCartRequestModel: AddCartRequestModel): ResultWrapper<CartModel> {

        val url = "$baseUrl/cart/1"

        return makeWebRequest(
            url = Url(url),
            method = HttpMethod.Post,
            body = AddToCartRequest.fromCartRequestModel(addCartRequestModel),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )

    }

    override suspend fun getCart(): ResultWrapper<CartModel> {

        val url = "$baseUrl/cart/1"

        return makeWebRequest(
            url = Url(url),
            method = HttpMethod.Get,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            }
        )

    }

    override suspend fun updateQuantity(cartItemModel: CartItemModel): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/1/${cartItemModel.id}"
        return makeWebRequest(url = Url(url),
            method = HttpMethod.Put,
            body = AddToCartRequest(
                productId = cartItemModel.productId,
                quantity = cartItemModel.quantity
            ),
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            })
    }

    override suspend fun deleteItem(cartItemId: Int, userId: Int): ResultWrapper<CartModel> {
        val url = "$baseUrl/cart/$userId/$cartItemId"
        return makeWebRequest(url = Url(url),
            method = HttpMethod.Delete,
            mapper = { cartItem: CartResponse ->
                cartItem.toCartModel()
            })
    }

    override suspend fun getCartSummary(userId: Int): ResultWrapper<CartSummary> {
        val url = "$baseUrl/checkout/$userId/summary"
        return makeWebRequest(url = Url(url),
            method = HttpMethod.Get,
            mapper = { cartSummary: CartSummaryResponse ->
                cartSummary.toCartSummary()
            })
    }

    override suspend fun placeOrder(address: AddressDomainModel, userId: Int): ResultWrapper<Long> {
        val dataModel = AddressDataModel.fromDomainAddress(address)
        val url = "$baseUrl/orders/$userId"
        return makeWebRequest(url = Url(url),
            method = HttpMethod.Post,
            body = dataModel,
            mapper = { orderRes: PlaceOrderResponse ->
                orderRes.data.id
            })
    }

    override suspend fun getOrderList(): ResultWrapper<OrdersListModel> {
        val url = "$baseUrl/orders/1"
        return makeWebRequest(url = Url(url),
            method = HttpMethod.Get,
            mapper = { ordersResponse: OrdersListResponse ->
                ordersResponse.toDomainResponse()
            })
    }

    suspend inline fun <reified T, R> makeWebRequest(
        url: Url,
        method:HttpMethod,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        noinline mapper: ((T) -> R)? = null
    ) : ResultWrapper<R> {

        return try {
            val response = client.request(url) {

                this.method = method

                url {

                    this.parameters.appendAll(Parameters.build {
                        params.forEach { (key, value) ->
                            append(key, value)
                        }
                    })

                }

                headers.forEach { (key, value) ->

                    header(key, value)
                }

                if (body != null){

                   setBody(body)

                }

                contentType(ContentType.Application.Json)

            }.body<T>()

            val result: R = mapper?.invoke(response) ?: response as R

            ResultWrapper.Success(result)

        }catch (e:Exception){

            ResultWrapper.Failure(e)

        }
    }


}