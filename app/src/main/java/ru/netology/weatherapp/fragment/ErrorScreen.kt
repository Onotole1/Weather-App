package ru.netology.weatherapp.fragment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.netology.weatherapp.R
import ru.netology.weatherapp.ui.theme.ComposeAppTheme
import ru.netology.weatherapp.ui.util.toast

@Composable
fun ErrorScreen(error: Throwable, onRetryClicked: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            error.message?.let {
                Text(text = it)
            }
            Button(onClick = onRetryClicked) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Composable
@Preview
fun ErrorScreenPreview() {
    ComposeAppTheme {
        val context = LocalContext.current
        ErrorScreen(error = RuntimeException("Test error")) {
            context.toast("Клац!")
        }
    }
}
