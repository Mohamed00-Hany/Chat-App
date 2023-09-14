package com.projects.domain.repositoriesContract

sealed interface TaskStates{
    data class TaskCompleted<Data>(val data: Data?): TaskStates
    data class TaskSucceed<Data>(val data: Data?): TaskStates
    data class TaskFailed(val error: String?): TaskStates
}