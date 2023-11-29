package hh.school.lesson_10_zemskov.data.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindBridgeRepository(
        networkBridgesRepository: NetworkBridgesRepository
    ): BridgesRepository
}