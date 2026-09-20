package com.wtduyuwnt.overrecipes.ui.components.video

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

private const val BRIDGE_NAME = "OverRecipesBridge"

private fun embedHtml(videoId: String): String = """
    <!doctype html>
    <html>
      <head>
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
        <style>
          html, body { margin: 0; padding: 0; background: #000; height: 100%; overflow: hidden; }
          #player { width: 100%; height: 100%; }
        </style>
      </head>
      <body>
        <div id="player"></div>
        <script src="https://www.youtube.com/iframe_api"></script>
        <script>
          function onYouTubeIframeAPIReady() {
            new YT.Player('player', {
              videoId: '$videoId',
              playerVars: {
                playsinline: 1,
                rel: 0,
                modestbranding: 1,
                origin: 'https://www.youtube.com'
              },
              events: {
                onError: function (event) { $BRIDGE_NAME.onPlayerError(event.data); }
              }
            });
          }
        </script>
      </body>
    </html>
""".trimIndent()

@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
@Composable
fun YouTubeVideoPlayer(
    videoId: String,
    modifier: Modifier = Modifier,
    onEmbedError: (Int) -> Unit = {},
    onFullscreenView: ((View?) -> Unit)? = null
) {
    val context = LocalContext.current
    val currentOnError by rememberUpdatedState(onEmbedError)
    val currentOnFullscreen by rememberUpdatedState(onFullscreenView)

    val webView = remember(videoId) {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setBackgroundColor(android.graphics.Color.BLACK)
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            webViewClient = WebViewClient()
            webChromeClient = object : WebChromeClient() {
                override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
                    currentOnFullscreen?.invoke(view)
                }

                override fun onHideCustomView() {
                    currentOnFullscreen?.invoke(null)
                }
            }
            addJavascriptInterface(
                object {
                    @JavascriptInterface
                    fun onPlayerError(code: Int) {
                        post { currentOnError(code) }
                    }
                },
                BRIDGE_NAME
            )
            loadDataWithBaseURL(
                "https://www.youtube.com",
                embedHtml(videoId),
                "text/html",
                "utf-8",
                null
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, webView) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> webView.onPause()
                Lifecycle.Event.ON_RESUME -> webView.onResume()
                else -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            (webView.parent as? ViewGroup)?.removeView(webView)
            webView.destroy()
        }
    }

    AndroidView(modifier = modifier, factory = { webView })
}
