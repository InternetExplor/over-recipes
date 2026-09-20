package com.wtduyuwnt.overrecipes.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.data.model.AppLanguage
import com.wtduyuwnt.overrecipes.data.model.ThemeMode
import com.wtduyuwnt.overrecipes.ui.screens.profile.components.ProfileHeader
import com.wtduyuwnt.overrecipes.ui.screens.profile.components.ProfileStatsCard
import com.wtduyuwnt.overrecipes.ui.screens.profile.components.SettingsOptionDialog
import com.wtduyuwnt.overrecipes.ui.screens.profile.components.SettingsRow
import com.wtduyuwnt.overrecipes.ui.state.ProfileUiState

private enum class OpenDialog { NONE, LANGUAGE, THEME }

@Composable
fun ProfileScreen(
    state: ProfileUiState,
    onLanguageSelect: (AppLanguage) -> Unit,
    onThemeSelect: (ThemeMode) -> Unit,
    onSignOut: () -> Unit,
    contentPadding: PaddingValues
) {
    var dialog by remember { mutableStateOf(OpenDialog.NONE) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
            .padding(bottom = contentPadding.calculateBottomPadding() + 28.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        ProfileHeader(name = state.name, subtitle = state.subtitle)

        Spacer(Modifier.height(24.dp))
        ProfileStatsCard(
            favoritesCount = state.favoritesCount,
            categoriesCount = state.categoriesCount
        )

        Spacer(Modifier.height(24.dp))
        Text(
            text = "Настройки",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(8.dp))
        SettingsRow(
            title = "Язык рецептов",
            value = state.language.label,
            onClick = { dialog = OpenDialog.LANGUAGE }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        SettingsRow(
            title = "Тема оформления",
            value = state.themeMode.label,
            onClick = { dialog = OpenDialog.THEME }
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        SettingsRow(title = "Источник данных", value = "Zira.uz")
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        SettingsRow(title = "Версия приложения", value = state.appVersion)

        Spacer(Modifier.height(28.dp))
        OutlinedButton(
            onClick = onSignOut,
            modifier = Modifier.fillMaxWidth().height(52.dp)
        ) {
            Text("Выйти из аккаунта")
        }
    }

    when (dialog) {
        OpenDialog.LANGUAGE -> SettingsOptionDialog(
            title = "Язык рецептов",
            options = AppLanguage.entries,
            selected = state.language,
            label = { it.label },
            onSelect = onLanguageSelect,
            onDismiss = { dialog = OpenDialog.NONE }
        )

        OpenDialog.THEME -> SettingsOptionDialog(
            title = "Тема оформления",
            options = ThemeMode.entries,
            selected = state.themeMode,
            label = { it.label },
            onSelect = onThemeSelect,
            onDismiss = { dialog = OpenDialog.NONE }
        )

        OpenDialog.NONE -> Unit
    }
}
