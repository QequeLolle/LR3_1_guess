package com.example.lr3_1_guess.domain

import com.example.lr3_1_guess.data.DownloadImage
import javax.inject.Inject

class ApplicationInternalInteractorImpl @Inject constructor(
    private val downloadImage: DownloadImage
) : ApplicationInternalInteractor {
    override suspend fun getContent(address: String): Pair<List<String>, List<String>>? = downloadImage.getContent(address)
}