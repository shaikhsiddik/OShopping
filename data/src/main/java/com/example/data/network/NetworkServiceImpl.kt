package com.example.data.network

import com.example.data.model.DataProductModel
import com.example.domain.model.Product
import com.example.domain.network.NetworkService
import com.example.domain.network.ResultWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.util.InternalAPI

@OptIn(InternalAPI::class)
class NetworkServiceImpl(val client: HttpClient): NetworkService {

    private val baseUrl = "https://fakestoreapi.com"

    override suspend fun getProducts(category: String?): ResultWrapper<List<Product>> {

        val url = if (category != null) "$baseUrl/products/category/$category" else "$baseUrl/products"

        return makeWebRequest(
            url = Url(url),
            method = HttpMethod.Get,
            mapper = { dataModule: List<DataProductModel> ->
                dataModule.map {

                    it.toProduct()

                }
            },
            params = mapOf("limit" to "10")
        )
    }

    override suspend fun getCategory(): ResultWrapper<List<String>> {
        val url = "$baseUrl/products/categories"

        return makeWebRequest<List<String>, List<String>>(
            url = Url(url),
            method = HttpMethod.Get
        )
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

                    this.body = body

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