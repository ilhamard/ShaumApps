package dev.nocturnbinary.dzikirkita.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.parameter
import io.ktor.client.request.prepareRequest
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import io.ktor.http.contentLength
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RequestHandler(val httpClient: HttpClient) {

    suspend inline fun <reified B, reified R> executeRequest(
        method: HttpMethod,
        urlPathSegments: List<Any>,
        body: B? = null,
        queryParams: Map<String, Any>? = null
    ): NetworkResult<R> {
        return withContext(Dispatchers.IO) {
            try {
                val response = httpClient.prepareRequest {
                    this.method = method
                    url {
                        val pathSegments = urlPathSegments.map { it.toString() }
                        appendPathSegments(pathSegments)
                    }
                    body?.let { setBody(it) }
                    queryParams?.let { params ->
                        params.forEach { (key, value) ->
                            parameter(key, value)
                        }
                    }
                }.execute().body<R>()

                NetworkResult.Success(response)
            } catch (e: Exception) {
                val networkException = if (e is ResponseException) {
                    if (e.response.contentLength() == 0L) {
                        NetworkException.UnknownException("No content received from server", e)
                    } else {
                        val errorBody = e.response.body<DefaultError>()
                        when (e.response.status) {
                            HttpStatusCode.Unauthorized -> NetworkException.UnauthorizedException(
                                errorBody.message,
                                e
                            )

                            HttpStatusCode.Forbidden -> NetworkException.ForbiddenException(
                                errorBody.message,
                                e
                            )

                            HttpStatusCode.BadRequest -> NetworkException.BadRequestException(
                                errorBody.message,
                                e
                            )

                            HttpStatusCode.NotFound -> NetworkException.NotFoundException(
                                errorBody.message,
                                e
                            )

                            else -> NetworkException.UnknownException(
                                errorBody.message,
                                e
                            )
                        }
                    }
                } else {
                    NetworkException.UnknownException("Unknown exception", e)
                }

                NetworkResult.Error(null, networkException)
            }
        }
    }

    suspend inline fun <reified R> get(
        urlPathSegments: List<Any>,
        queryParams: Map<String, Any>? = null,
    ): NetworkResult<R> = executeRequest<Any, R>(
        method = HttpMethod.Get,
        urlPathSegments = urlPathSegments.toList(),
        queryParams = queryParams,
    )

    suspend inline fun <reified B, reified R> post(
        urlPathSegments: List<Any>,
        body: B? = null,
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Post,
        urlPathSegments = urlPathSegments.toList(),
        body = body,
    )

    suspend inline fun <reified B, reified R> put(
        urlPathSegments: List<Any>,
        body: B? = null
    ): NetworkResult<R> = executeRequest(
        method = HttpMethod.Put,
        urlPathSegments = urlPathSegments.toList(),
        body = body
    )
}