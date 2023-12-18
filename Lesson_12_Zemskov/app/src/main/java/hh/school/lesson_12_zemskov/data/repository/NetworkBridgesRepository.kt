package hh.school.lesson_12_zemskov.data.repository

import hh.school.lesson_12_zemskov.data.network.BridgesApiService
import hh.school.lesson_12_zemskov.data.network.model.NetworkBridge
import hh.school.lesson_12_zemskov.ui.model.Bridge
import javax.inject.Inject

class NetworkBridgesRepository @Inject constructor(
    private val apiService: BridgesApiService
) : BridgesRepository {
    override suspend fun getBridges(): List<Bridge> =
        apiService.getBridges().map(NetworkBridge::asInternalModel)

    override suspend fun getBridgeById(id: Int): Bridge =
        apiService.getBridgeById(id).asInternalModel()
}