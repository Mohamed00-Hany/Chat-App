package com.projects.domain.useCases

import com.projects.domain.models.Room
import com.projects.domain.repositoriesContract.TaskStates
import com.projects.domain.repositoriesContract.rooms.RoomsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertRoom @Inject constructor(private val roomsRepository: RoomsRepository) {
    suspend operator fun invoke(room: Room): Flow<TaskStates>
    {
        return roomsRepository.insertRoom(room)
    }
}