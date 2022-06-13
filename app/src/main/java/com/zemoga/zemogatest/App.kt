package com.zemoga.zemogatest

import android.app.Application
import com.zemoga.data.local.cacheLocalModule
import com.zemoga.data.remote.remoteModule
import com.zemoga.data.repository.repositoryModule
import com.zemoga.domain.usecases.useCasesModule
import com.zemoga.postdetail.postDetailModule
import com.zemoga.posts.postsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    remoteModule,
                    cacheLocalModule,
                    repositoryModule,
                    useCasesModule,
                    postsModule,
                    postDetailModule
                )
            )
        }
    }

}