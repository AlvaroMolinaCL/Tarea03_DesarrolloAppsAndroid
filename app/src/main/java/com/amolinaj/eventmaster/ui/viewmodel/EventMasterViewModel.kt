package com.amolinaj.eventmaster.ui.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amolinaj.eventmaster.data.repository.category.CategoryRepository
import com.amolinaj.eventmaster.data.repository.event.EventRepository
import com.amolinaj.eventmaster.ui.state.CategoryFormValidationErrors
import com.amolinaj.eventmaster.ui.state.EventFormValidationErrors
import com.amolinaj.eventmaster.ui.state.EventMasterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle
import javax.inject.Inject
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class EventMasterViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val strictDateFormatter by lazy {
        DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT)
    }

    var uiState by mutableStateOf(EventMasterUiState())
        private set

    init {
        observeUiState()
    }

    private fun observeUiState() {
        viewModelScope.launch {
            combine(
                categoryRepository.observeAll(),
                eventRepository.observeAll()
            ) { categories, events ->
                EventMasterUiState(categories = categories, events = events)
            }.collect { state ->
                uiState = state
            }
        }
    }

    fun validateCategory(name: String, description: String): CategoryFormValidationErrors {
        val trimmedName = name.trim()
        val trimmedDescription = description.trim()

        val nameError = when {
            trimmedName.isBlank() -> "La categoría es obligatoria"
            trimmedName.length < 3 -> "Debe tener al menos 3 caracteres"
            uiState.categories.any { it.name.equals(trimmedName, ignoreCase = true) } -> "La categoría ya existe"
            else -> null
        }

        val descriptionError = when {
            trimmedDescription.length > 120 -> "Máximo 120 caracteres"
            else -> null
        }

        return CategoryFormValidationErrors(
            nameError = nameError,
            descriptionError = descriptionError
        )
    }

    fun addCategory(name: String, description: String): CategoryFormValidationErrors {
        val errors = validateCategory(name = name, description = description)
        if (errors.hasErrors) return errors

        viewModelScope.launch {
            categoryRepository.insert(
                name = name.trim(),
                description = description.trim()
            )
        }
        return CategoryFormValidationErrors()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validateEvent(
        title: String,
        description: String,
        date: String,
        location: String,
        categoryId: Int?,
        imageResName: String,
        imageExists: Boolean
    ): EventFormValidationErrors {
        val titleError = when {
            title.isBlank() -> "El título es obligatorio"
            title.trim().length < 4 -> "El título debe tener al menos 4 caracteres"
            else -> null
        }

        val descriptionError = when {
            description.isBlank() -> "La descripción es obligatoria"
            description.trim().length < 10 -> "La descripción debe tener al menos 10 caracteres"
            else -> null
        }

        val dateError = when {
            date.isBlank() -> "La fecha es obligatoria"
            !isValidDate(date.trim()) -> "La fecha debe tener el formato DD/MM/AAAA y ser válida"
            else -> null
        }

        val locationError = when {
            location.isBlank() -> "La ubicación es obligatoria"
            else -> null
        }

        val categoryError = when {
            categoryId == null -> "Selecciona una categoría"
            uiState.categories.none { it.id == categoryId } -> "Categoría inválida"
            else -> null
        }

        val imageError = when {
            imageResName.isBlank() -> null
            !imageExists -> "No se encontró la imagen en drawable"
            else -> null
        }

        return EventFormValidationErrors(
            titleError = titleError,
            descriptionError = descriptionError,
            dateError = dateError,
            locationError = locationError,
            categoryError = categoryError,
            imageError = imageError
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEvent(
        title: String,
        description: String,
        date: String,
        location: String,
        categoryId: Int?,
        imageResName: String,
        imageExists: Boolean
    ): EventFormValidationErrors {
        val errors = validateEvent(
            title = title,
            description = description,
            date = date,
            location = location,
            categoryId = categoryId,
            imageResName = imageResName,
            imageExists = imageExists
        )

        if (errors.hasErrors) return errors

        viewModelScope.launch {
            eventRepository.insert(
                categoryId = categoryId!!,
                title = title.trim(),
                description = description.trim(),
                date = date.trim(),
                location = location.trim(),
                imageResName = imageResName.trim().takeIf { it.isNotBlank() }
            )
        }
        return EventFormValidationErrors()
    }

    fun getEventsByCategory(categoryId: Int): List<com.amolinaj.eventmaster.ui.model.EventItem> {
        return uiState.events.filter { it.categoryId == categoryId }
    }

    fun getEventById(eventId: Int): com.amolinaj.eventmaster.ui.model.EventItem? {
        return uiState.events.find { it.id == eventId }
    }

    fun getCategoryById(categoryId: Int): com.amolinaj.eventmaster.ui.model.EventCategory? {
        return uiState.categories.find { it.id == categoryId }
    }

    private fun isValidDate(value: String): Boolean {
        return try {
            LocalDate.parse(value, strictDateFormatter)
            true
        } catch (_: Exception) {
            false
        }
    }
}
