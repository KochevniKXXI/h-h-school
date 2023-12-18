package hh.school.lesson_12_zemskov.di

import dagger.Binds
import dagger.Module
import hh.school.lesson_12_zemskov.data.repository.BridgesRepository
import hh.school.lesson_12_zemskov.data.repository.NetworkBridgesRepository
import hh.school.lesson_12_zemskov.data.repository.OfflineReminderRepository
import hh.school.lesson_12_zemskov.data.repository.ReminderRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindBridgesRepository(networkBridgesRepository: NetworkBridgesRepository): BridgesRepository

    @Binds
    @Singleton
    fun bindReminderRepository(offlineReminderRepository: OfflineReminderRepository): ReminderRepository
}