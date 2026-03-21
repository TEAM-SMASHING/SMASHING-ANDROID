package com.smashing.app.data.di.report

import com.smashing.app.data.repository.api.ReportRepository
import com.smashing.app.data.repository.impl.ReportRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReportRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindReportRepository(
        impl: ReportRepositoryImpl,
    ): ReportRepository
}
