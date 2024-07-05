package ru.netology.weatherapp.fragment.forecastdetails

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.netology.weatherapp.ui.theme.ComposeAppTheme

@Composable
fun TextItem(model: ForecastDetailsUiModel.TextUiModel) {
    Text(fontSize = 16.sp, text = model.text, modifier = Modifier.padding(16.dp))
}

@Composable
@Preview(showBackground = true)
fun TextItemPreview() {
    ComposeAppTheme {
        TextItem(model = ForecastDetailsUiModel.TextUiModel("Восход: 05:12"))
    }
}