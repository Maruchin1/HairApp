package com.example.corev2.database

import kotlinx.coroutines.Job

interface DatabaseInitializer {

    fun checkIfInitialized(): Job
}