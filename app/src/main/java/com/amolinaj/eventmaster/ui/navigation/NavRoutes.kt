package com.amolinaj.eventmaster.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object AddCategoryRoute

@Serializable
data class AddEventRoute(val categoryId: Int? = null)

@Serializable
data class EventDetailRoute(val eventId: Int)
