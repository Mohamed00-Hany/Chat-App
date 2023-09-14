package com.projects.domain.useCases

import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRooms @Inject constructor(private val roomsRepository: RoomsRepository) {
    suspend operator fun invoke():Flow<TaskStates>
    {
        return roomsRepository.getAllRooms()
    }
}