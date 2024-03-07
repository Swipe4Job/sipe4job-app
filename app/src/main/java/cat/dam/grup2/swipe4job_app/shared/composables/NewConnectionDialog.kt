package cat.dam.grup2.swipe4job_app.shared.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cat.dam.grup2.swipe4job_app.R
import cat.dam.grup2.swipe4job_app.shared.ui.theme.animationFontRegular
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun NewConnectionDialog(onDismiss: () -> Unit, callback: suspend () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            elevation = CardDefaults.cardElevation(
              defaultElevation = 16.dp
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                AnimationItem(
                    animationResId = R.raw.connection_animation,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    callback = callback
                )
                ConnectionText()
            }
        }
    }
}

@Composable
private fun AnimationItem(
    animationResId: Int, modifier: Modifier = Modifier
        .fillMaxSize()
        .aspectRatio(1f), callback: suspend () -> Unit
) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(
            animationResId
        )
    )

    LaunchedEffect(true) {
        callback()
    }

    LottieAnimation(
        composition = composition,
        modifier = modifier,
        speed = 2.5f
    )
}

@Composable
private fun ConnectionText() {
    val connectionText = buildAnnotatedString {
        append("It's a ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append("connection")
        }
    }

    SelectionContainer {
        Text(
            text = connectionText,
            style = MaterialTheme.typography.headlineLarge,
            fontFamily = animationFontRegular,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}