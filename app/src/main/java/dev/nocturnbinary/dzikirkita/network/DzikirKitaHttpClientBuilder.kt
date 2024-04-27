package dev.nocturnbinary.dzikirkita.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.cio.endpoint
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class DzikirKitaHttpClientBuilder {

    private lateinit var protocol: URLProtocol
    private lateinit var host: String
    private var port: Int? = null

    fun protocol(protocol: URLProtocol) = apply { this.protocol = protocol }

    fun host(host: String) = apply { this.host = host }

    fun port(port: Int) = apply { this.port = port }

    fun build(): HttpClient {
        return HttpClient(CIO) {

            expectSuccess = true

            engine {
                endpoint {
                    keepAliveTime = 6000
                    connectTimeout = 6000
                    connectAttempts = 3
                }
            }

            defaultRequest {
                url {
                    protocol = this@DzikirKitaHttpClientBuilder.protocol
                    host = this@DzikirKitaHttpClientBuilder.host
                    this@DzikirKitaHttpClientBuilder.port?.let { port = it }
                }

                header("Content-Type", "application/json")
            }

            install(ContentNegotiation){
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(Logging){
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
}