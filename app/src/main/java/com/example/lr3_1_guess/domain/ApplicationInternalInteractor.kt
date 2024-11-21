package com.example.lr3_1_guess.domain

interface ApplicationInternalInteractor {
    suspend fun getContent(address: String): Pair<List<String>, List<String>>?
}