package hh.school.lesson_10_zemskov.data.repository

import hh.school.lesson_10_zemskov.data.network.BridgesApiService
import hh.school.lesson_10_zemskov.data.network.model.NetworkBridge
import hh.school.lesson_10_zemskov.model.Bridge
import javax.inject.Inject

class NetworkBridgesRepository @Inject constructor(
    private val bridgesApiService: BridgesApiService
) : BridgesRepository {
    override suspend fun getBridgesPoints(): List<Bridge> =
        bridgesApiService.getBridgesPoints().map(NetworkBridge::asInternalModel)

    override suspend fun getBridgeInfoById(id: Int): Bridge =
        bridgesApiService.getBridgeInfoById(id).asInternalModel()
}