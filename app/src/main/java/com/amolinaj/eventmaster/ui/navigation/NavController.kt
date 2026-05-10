package com.amolinaj.eventmaster.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.toRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amolinaj.eventmaster.ui.screens.AddCategoryScreen
import com.amolinaj.eventmaster.ui.screens.AddEventScreen
import com.amolinaj.eventmaster.ui.screens.EventDetailScreen
import com.amolinaj.eventmaster.ui.screens.HomeScreen
import com.amolinaj.eventmaster.ui.viewmodel.EventMasterViewModel

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DiscouragedApi", "LocalContextResourcesRead")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val eventMasterViewModel: EventMasterViewModel = hiltViewModel()
    val context = LocalContext.current

    val resolveImageResId: (String?) -> Int = { imageResName ->
        if (imageResName.isNullOrBlank()) {
            0
        } else {
            context.resources.getIdentifier(imageResName, "drawable", context.packageName)
        }
    }

    NavHost(navController = navController, startDestination = HomeRoute) {
        composable<HomeRoute> {
            HomeScreen(
                uiState = eventMasterViewModel.uiState,
                onCreateCategory = { navController.navigate(AddCategoryRoute) },
                onCreateEvent = { categoryId ->
                    navController.navigate(AddEventRoute(categoryId = categoryId))
                },
                onOpenEventDetail = { eventId ->
                    navController.navigate(EventDetailRoute(eventId = eventId))
                },
                getEventsByCategory = eventMasterViewModel::getEventsByCategory,
                resolveImageResId = resolveImageResId
            )
        }

        composable<AddCategoryRoute> {
            AddCategoryScreen(
                viewModel = eventMasterViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable<AddEventRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<AddEventRoute>()
            AddEventScreen(
                viewModel = eventMasterViewModel,
                categories = eventMasterViewModel.uiState.categories,
                preselectedCategoryId = route.categoryId,
                onBack = { navController.popBackStack() }
            )
        }

        composable<EventDetailRoute> { backStackEntry ->
            val route = backStackEntry.toRoute<EventDetailRoute>()
            val event = eventMasterViewModel.getEventById(route.eventId)
            val category = event?.categoryId?.let(eventMasterViewModel::getCategoryById)
            EventDetailScreen(
                event = event,
                category = category,
                imageResId = resolveImageResId(event?.imageResName),
                onBack = { navController.popBackStack() }
            )
        }
    }
}