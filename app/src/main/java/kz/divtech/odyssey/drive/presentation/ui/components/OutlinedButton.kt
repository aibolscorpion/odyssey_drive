package kz.divtech.odyssey.drive.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kz.divtech.odyssey.drive.common.Variables
import kz.divtech.odyssey.drive.presentation.theme.ColorPrimary


@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.outlinedShape,
    elevation: ButtonElevation? = null,
    color: Color = ColorPrimary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit = {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight(600),
                color = color,
            )
        )
    }){

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(Variables.ButtonHeight),
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White,
            contentColor = color),
        elevation = elevation,
        border = BorderStroke(
            width = 1.dp,
            color = color,
        ),
        contentPadding = contentPadding,
        interactionSource = interactionSource,
        content = content
    )

}