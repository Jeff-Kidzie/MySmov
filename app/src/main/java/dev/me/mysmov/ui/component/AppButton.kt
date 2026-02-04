package dev.me.mysmov.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    type: ButtonType = ButtonType.Primary,
    prefixIcon : @Composable (() -> Unit)? = null,
    text: String = "",
    onClickButton: () -> Unit = {}
) {
    val bgButton = when (type) {
        is ButtonType.Primary -> MaterialTheme.colorScheme.primary
        is ButtonType.Secondary -> Color.Transparent
    }

    val borderStroke = when (type) {
        is ButtonType.Primary -> null
        is ButtonType.Secondary -> BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    }
    Button(
        modifier = modifier.padding(10.dp),
        onClick = onClickButton,
        border = borderStroke,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = bgButton)
    ) {
        val textColor = when (type) {
            is ButtonType.Primary -> Color(0xFFFFFFFF)
            is ButtonType.Secondary -> MaterialTheme.colorScheme.primary
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (prefixIcon != null) {
                prefixIcon()
            }
            Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = textColor))
        }

    }
}

sealed class ButtonType {
    object Primary : ButtonType()
    object Secondary : ButtonType()
}