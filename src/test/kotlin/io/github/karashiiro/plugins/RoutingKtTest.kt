package io.github.karashiiro.plugins

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutingKtTest {
    @Test
    fun testGetWorlds() = testApplication {
        application {
            install(ContentNegotiation) {
                json()
            }
            configureRouting()
        }
        client.get("/worlds").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}