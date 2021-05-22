package com.example.corev2.navigation

abstract class EditCareSchemaDestination : Destination<EditCareSchemaDestination.Params>() {

    data class Params(val careSchemaId: Long)
}