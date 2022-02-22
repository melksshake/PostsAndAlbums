package com.melkonian.postsandalbums.utils

import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.melkonian.postsandalbums.R
import com.melkonian.postsandalbums.presentation.base.BaseViewModel

fun Fragment.setupNavigation(viewModel: BaseViewModel) {
    viewModel.navigationCommands.observe(viewLifecycleOwner) { command ->
        when (command) {
            is BaseViewModel.NavigationCommand.To -> {
                if (command.popUpInclusive) {
                    findNavController().apply {
                        currentDestination?.let { currentDestination ->
                            navigate(
                                command.directions,
                                NavOptions.Builder().setPopUpTo(currentDestination.id, true).build()
                            )
                        } ?: navigate(command.directions)
                    }
                } else {
                    findNavController().navigate(command.directions)
                }
            }
            BaseViewModel.NavigationCommand.Up -> findNavController().navigateUp()
        }
    }
}

fun Fragment.setupToolbar(toolbar: Toolbar) {
    val navController = findNavController()
    val configuration = AppBarConfiguration(navController.graph)

    toolbar.setNavigationOnClickListener { NavigationUI.navigateUp(navController, configuration) }
    navController.currentDestination?.let { currentDestination ->
        val isTopLevelDestination = matchDestinations(
            currentDestination,
            configuration.topLevelDestinations
        )
        toolbar.navigationIcon = if (isTopLevelDestination) {
            null
        } else {
            ContextCompat.getDrawable(toolbar.context, R.drawable.ic_arrow_back)
        }

        currentDestination.label?.let { label -> toolbar.title = label }
    }
}

private fun matchDestinations(
    destination: NavDestination,
    destinationIds: Set<Int?>
): Boolean {
    var currentDestination: NavDestination? = destination
    do {
        if (destinationIds.contains(currentDestination!!.id)) {
            return true
        }
        currentDestination = currentDestination.parent
    } while (currentDestination != null)
    return false
}