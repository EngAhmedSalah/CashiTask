package com.example.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class Transaction(val id: String,
                       val amount: Double,
                       val asset: String,
                       val assetType: String ,
                       val type: String ,
                       val state: String ,
                       val created_at: String)

val transactionStorage = mutableListOf<Transaction>()

