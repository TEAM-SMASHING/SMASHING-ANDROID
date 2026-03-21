package com.smashing.app.data.di.report

import com.smashing.app.data.remote.datasource.api.ReportRemoteDataSource
import com.smashing.app.data.remote.datasource.impl.ReportRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ReportDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindReportRemoteDataSource(
        impl: ReportRemoteDataSourceImpl,
    ): ReportRemoteDataSource
}
