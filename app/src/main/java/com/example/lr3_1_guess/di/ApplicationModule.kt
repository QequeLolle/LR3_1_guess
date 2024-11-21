package com.example.lr3_1_guess.di

import com.example.lr3_1_guess.data.DownloadImage
import com.example.lr3_1_guess.data.DownloadImageImpl
import com.example.lr3_1_guess.domain.ApplicationInternalInteractor
import com.example.lr3_1_guess.domain.ApplicationInternalInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {
    @Binds
    @Singleton
    fun bindDownloadImage(downloadImageImpl: DownloadImageImpl): DownloadImage

    @Binds
    @Singleton
    fun bindInternalInteractor(applicationInternalInteractorImpl: ApplicationInternalInteractorImpl): ApplicationInternalInteractor

    companion object {
        @Provides
        @IoDispatcher
        @Singleton
        fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher