package com.choiceTech.choicedxself.ui.di

import com.choiceTech.choicedxself.core.data.CrmRepositoryImpl
import com.choiceTech.choicedxself.core.domain.repository.CrmRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CrmModule {
    @Binds
    abstract fun bindCrmRepository(
        impl: CrmRepositoryImpl
    ): CrmRepository
}