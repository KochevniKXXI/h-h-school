package hh.school.lesson_12_zemskov.data.repository

import hh.school.lesson_12_zemskov.ui.model.Bridge

interface BridgesRepository {
    suspend fun getBridges(): List<Bridge>
    suspend fun getBridgeById(id: Int): Bridge
}