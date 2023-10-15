package com.example.routes

import com.example.model.Transaction
import com.example.model.TransactionResponse
import com.example.model.transactionStorage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.transactionRouting() {
    route("/transaction/fee") {
        get {
            if (transactionStorage.isNotEmpty()) {
                call.respond(transactionStorage)
            } else {
                call.respondText("No transaction found", status = HttpStatusCode.OK)
            }
        }
        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val transaction =
                transactionStorage.find { it.id == id } ?: return@get call.respondText(
                    "No transaction with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(transaction)
        }
        post {
            try {
                val transaction = call.receive<Transaction>()
                val responseModel = processRequest(transaction)
                transactionStorage.add(transaction)
                call.respond(HttpStatusCode.OK, responseModel)
            } catch (e: Exception) {
                call.respondText("cannot process this transaction", status = HttpStatusCode.OK)
            }
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (transactionStorage.removeIf { it.id == id }) {
                call.respondText("Transaction removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}

fun processRequest(transaction: Transaction): TransactionResponse {
    // Perform some processing with the received data
    return TransactionResponse(transaction.id, transaction.amount , transaction.asset , transaction.type , 1.5 , 0.0015 , "Standard fee rate of " +
            "0.15%")
}