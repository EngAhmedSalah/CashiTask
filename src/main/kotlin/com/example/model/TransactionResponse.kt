package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponse(val id: String, val amount: Double, val asset: String, val type: String , val fee: Double , val rate : Double , val description : String)

