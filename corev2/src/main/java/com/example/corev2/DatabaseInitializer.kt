package com.example.corev2

import kotlinx.coroutines.Job

interface DatabaseInitializer {

    fun checkIfInitialized(): Job
}