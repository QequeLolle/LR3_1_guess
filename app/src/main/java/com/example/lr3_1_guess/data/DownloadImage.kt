package com.example.lr3_1_guess.data

interface DownloadImage {
    suspend fun getContent(address: String): Pair<List<String>, List<String>>?
}