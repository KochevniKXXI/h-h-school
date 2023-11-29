package hh.school.lesson_10_zemskov.data.repository

import hh.school.lesson_10_zemskov.model.Bridge

interface BridgesRepository {
    suspend fun getBridgesPoints(): List<Bridge>
    suspend fun getBridgeInfoById(id: Int): Bridge
}