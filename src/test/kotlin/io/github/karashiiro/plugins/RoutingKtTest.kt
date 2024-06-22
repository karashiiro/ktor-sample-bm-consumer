package io.github.karashiiro.plugins

import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class RoutingKtTest {

    @Test
    fun testGetWorlds() = testApplication {
        application {
            configureRouting()
        }
        client.get("/worlds").apply {
            TODO("Please write your test here")
        }
    }
}