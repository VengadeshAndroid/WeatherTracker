package com.nooro.weather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.nooro.weather.R
import com.nooro.weather.ui.theme.Color2C2C2C
import com.nooro.weather.ui.theme.Dp16
import com.nooro.weather.ui.theme.Dp8
import com.nooro.weather.ui.theme.Sp16
import com.nooro.weather.ui.theme.Sp30
import com.nooro.weather.ui.theme.Typography

@Composable
fun NoDataFound(message: String = stringResource(id = R.string.no_city_selected)) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(Dp16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = Typography.displayMedium,
            fontSize = Sp30,
            color = Color2C2C2C,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(Dp8))

        Text(
            text = stringResource(id = R.string.please_search),
            style = Typography.displayMedium,
            fontSize = Sp16,
            fontWeight = FontWeight.SemiBold,
            color = Color2C2C2C,
            textAlign = TextAlign.Center
        )
    }
}