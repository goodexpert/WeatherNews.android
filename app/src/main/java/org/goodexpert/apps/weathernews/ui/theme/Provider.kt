package org.goodexpert.apps.weathernews.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.github.zsoltk.compose.backpress.BackPressHandler
import com.github.zsoltk.compose.backpress.LocalBackPressHandler
import org.goodexpert.apps.weathernews.network.ApiClient

val LocalApiClient = staticCompositionLocalOf<ApiClient> {
    error("No ApiClient provided")
}

@Composable
fun AppLocalProvider(
    apiClient: ApiClient,
    backPressHandler: BackPressHandler,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalApiClient provides apiClient,
        LocalBackPressHandler provides backPressHandler,
        content = content
    )
}
