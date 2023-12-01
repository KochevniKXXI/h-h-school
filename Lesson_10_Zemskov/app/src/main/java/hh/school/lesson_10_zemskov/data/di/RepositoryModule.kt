package hh.school.lesson_10_zemskov.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hh.school.lesson_10_zemskov.data.repository.BridgesRepository
import hh.school.lesson_10_zemskov.data.repository.NetworkBridgesRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindBridgeRepository(
        networkBridgesRepository: NetworkBridgesRepository
    ): BridgesRepository
}