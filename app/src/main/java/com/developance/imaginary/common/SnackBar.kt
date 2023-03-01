package com.developance.imaginary.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ErrorSnackBar(
    message: String,
    withAction: Boolean = false,
    snackbarHostState: SnackbarHostState,
    onActionPerform: () -> Unit = {}
) {
    LaunchedEffect(key1 = onActionPerform) {

        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = if (withAction) "Retry" else null,
            duration = SnackbarDuration.Indefinite,
        )
        when (result) {
            SnackbarResult.Dismissed -> snackbarHostState.currentSnackbarData?.dismiss()
            SnackbarResult.ActionPerformed -> onActionPerform()
        }
    }
}

suspend fun InformativeSnackBar(
    message: String,
    snackbarHostState: SnackbarHostState,
) {
        snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short,
        )
}