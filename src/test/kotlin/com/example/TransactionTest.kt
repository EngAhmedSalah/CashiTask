package com.example

import com.example.model.Transaction
import com.example.model.TransactionResponse
import com.example.plugins.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlin.test.*
class TransactionTest
{

    @Test
    fun testPostCustomer() = testApplication {

        val response = client.post("/transaction/fee") {
            contentType(ContentType.Application.Json)
            setBody(Transaction("txn_001",1000.0, "USD","FIAT","Mobile Top Up","SETTLED - PENDING FEE","2023-08-30 15:42:17.610059"))
        }
            assertEquals(response.bodyAsText() , TransactionResponse("txn_001", 1000.0, "USD", "Mobile Top Up", 1.5 , 0.0015 , "Standard fee rate of 0.15%").toString())
    }
}