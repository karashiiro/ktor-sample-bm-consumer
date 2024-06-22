package io.github.karashiiro

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class WorldFields(@SerialName("Name") val name: String)

@Serializable
data class World(@SerialName("row_id") val id: Int, val fields: WorldFields)

@Serializable
data class SheetResponse<E>(val rows: List<E>)

object BMClient {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun worlds() = flow {
        var rowsAfter = -1
        var done = false
        while (!done) {
            val pageUrl = "https://beta.xivapi.com/api/1/sheet/World" + if (rowsAfter < 0) "" else "?after=$rowsAfter"
            val response: SheetResponse<World> = client.get(pageUrl).body()

            val lastRowId = response.rows.map { it.id }.lastOrNull()
            if (lastRowId != null) {
                rowsAfter = lastRowId
            } else {
                done = true
            }

            response.rows.forEach { emit(it) }
        }
    }
}