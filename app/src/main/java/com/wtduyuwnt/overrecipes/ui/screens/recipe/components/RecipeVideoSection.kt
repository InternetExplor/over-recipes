package com.wtduyuwnt.overrecipes.ui.screens.recipe.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wtduyuwnt.overrecipes.ui.components.video.ExoVideoPlayer
import com.wtduyuwnt.overrecipes.ui.components.video.FullscreenAndroidView
import com.wtduyuwnt.overrecipes.ui.components.video.FullscreenVideoDialog
import com.wtduyuwnt.overrecipes.ui.components.video.YouTubeFallbackCard
import com.wtduyuwnt.overrecipes.ui.components.video.YouTubeVideoPlayer
import com.wtduyuwnt.overrecipes.ui.components.video.rememberExoPlayer
import com.wtduyuwnt.overrecipes.util.VideoUrls

@Composable
fun RecipeVideoSection(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val youTubeId = remember(videoUrl) { VideoUrls.youTubeId(videoUrl) }
    val isDirect = remember(videoUrl) { VideoUrls.isDirectMedia(videoUrl) }
    val context = LocalContext.current

    Column(modifier.padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(28.dp))
        Text(
            text = "Видеорецепт",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(Modifier.height(12.dp))

        when {
            youTubeId != null -> YouTubeSection(
                videoId = youTubeId,
                onOpenExternal = { context.openYouTube(youTubeId) }
            )

            isDirect -> DirectVideoSection(videoUrl)

            else -> TextButton(onClick = { context.openLink(videoUrl) }) {
                Text("Открыть видео во внешнем приложении")
            }
        }
    }
}

@Composable
private fun YouTubeSection(
    videoId: String,
    onOpenExternal: () -> Unit
) {
    var fullscreenView by remember { mutableStateOf<View?>(null) }
    var embedError by remember(videoId) { mutableStateOf<Int?>(null) }

    val error = embedError
    if (error != null) {
        YouTubeFallbackCard(
            videoId = videoId,
            message = embedErrorMessage(error),
            onOpenExternal = onOpenExternal
        )
        return
    }

    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Black)
    ) {
        YouTubeVideoPlayer(
            videoId = videoId,
            modifier = Modifier.fillMaxSize(),
            onEmbedError = { code -> embedError = code },
            onFullscreenView = { view -> fullscreenView = view }
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(onClick = onOpenExternal) {
            Text("Смотреть в YouTube")
        }
    }

    fullscreenView?.let { view ->
        FullscreenAndroidView(view = view, onDismiss = { fullscreenView = null })
    }
}

@Composable
private fun DirectVideoSection(url: String) {
    val player = rememberExoPlayer(url)
    var fullscreen by remember { mutableStateOf(false) }

    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Black)
    ) {
        if (!fullscreen) {
            ExoVideoPlayer(
                player = player,
                modifier = Modifier.fillMaxSize(),
                onFullscreen = { fullscreen = true }
            )
        }
    }

    if (fullscreen) {
        FullscreenVideoDialog(onDismiss = { fullscreen = false }) {
            ExoVideoPlayer(
                player = player,
                modifier = Modifier.fillMaxSize(),
                onFullscreen = { fullscreen = false }
            )
        }
    }
}

private fun embedErrorMessage(code: Int): String = when (code) {
    101, 150 -> "Автор запретил встраивание этого видео — откройте его в YouTube."
    2 -> "Ссылка на видео повреждена."
    5 -> "Плеер не поддерживает это видео на устройстве."
    100 -> "Видео удалено или закрыто автором."
    else -> "Видео недоступно во встроенном плеере — откройте его в YouTube."
}

private fun Context.openYouTube(videoId: String) {
    val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$videoId"))
    try {
        startActivity(appIntent)
    } catch (error: ActivityNotFoundException) {
        openLink("https://www.youtube.com/watch?v=$videoId")
    }
}

private fun Context.openLink(url: String) {
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    } catch (error: ActivityNotFoundException) {
        // no browser installed
    }
}
