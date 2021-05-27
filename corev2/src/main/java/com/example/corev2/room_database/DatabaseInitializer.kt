package com.example.corev2.room_database

import kotlinx.coroutines.Job

interface DatabaseInitializer {

    fun checkIfInitialized(): Job

    fun reset(): Job
}