package com.zemoga.zemogatest

import android.app.Application
import com.zemoga.data.remote.remoteModule
import com.zemoga.data.repository.repositoryModule
import com.zemoga.domain.usecases.useCasesModule
import com.zemoga.posts.postsModule
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            modules(
                listOf(
                    remoteModule,
                    repositoryModule,
                    useCasesModule,
                    postsModule
                )
            )
        }
    }

}