package cat.dam.grup2.swipe4job_app.features.candidate.screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.dam.grup2.swipe4job_app.R
import cat.dam.grup2.swipe4job_app.shared.composables.CustomDropdown
import cat.dam.grup2.swipe4job_app.shared.composables.CustomTextFieldMaxChar
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import cat.dam.grup2.swipe4job_app.CustomError
import cat.dam.grup2.swipe4job_app.features.candidate.state.CandidateProfileViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AddLanguage(navController: NavController) {
    var selectedLanguage by remember { mutableStateOf("") }
    var selectedLevel by remember { mutableStateOf<LanguageLevel?>(null) }
    var academicTitle by remember { mutableStateOf("") }
    var candidateProfileViewModel = CandidateProfileViewModel.getInstance()
    var languagesList = candidateProfileViewModel.languages

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_icon_description),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.addLanguage_text),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .weight(1f)
                        )
                        Text(
                            /* TODO: verify that Language and Level fields are not empty */
                            text = stringResource(id = R.string.save_text),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .clickable {
                                    languagesList.add(
                                        LanguageSkill(
                                            selectedLanguage,
                                            selectedLevel!!,
                                            academicTitle
                                        )
                                    )
                                    /* TODO: Save data in database */
                                    navController.popBackStack()
                                }
                                .padding(end = 16.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                AddLanguageContent(
                    onLanguageChange = { selectedLanguage = it },
                    onLevelChange = { selectedLevel = it },
                    onTitleChange = { academicTitle = it }
                )
            }
        }
    }
}

@Composable
fun AddLanguageContent(
    onLanguageChange: (String) -> Unit,
    onLevelChange: (LanguageLevel) -> Unit,
    onTitleChange: (String) -> Unit
) {
    var languageText = stringResource(id = R.string.language_text)
    var selectedLanguageItem by remember { mutableStateOf(languageText) }
    var languageOptions = stringArrayResource(R.array.languages_array).toList()

    var levelText = stringResource(id = R.string.level_text)
    var selectedLevelItem by remember { mutableStateOf(levelText) }
    var levelOptions = stringArrayResource(R.array.languages_level_array).toList()

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            CustomDropdown(
                placeholder = selectedLanguageItem,
                items = languageOptions
            ) {
                onLanguageChange(it)
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomDropdown(
                placeholder = selectedLevelItem,
                items = levelOptions
            ) {
                onLevelChange(toLanguageLevel(context, text = it))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Title - Title or certificate
            Text(
                stringResource(id = R.string.academicTitle_text),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                stringResource(id = R.string.commentsAddLanguage_text),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Text field for the comments
            var academicTitle = remember { mutableStateOf("") }

            LaunchedEffect(academicTitle) {
                onTitleChange(academicTitle.value)
            }

            CustomTextFieldMaxChar(
                descriptionState = academicTitle,
                maxCharacters = 200,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                )
            )
        }
    }
}


fun toLanguageLevel(context: Context, text: String): LanguageLevel {
    return when (text) {
        context.resources.getStringArray(R.array.languages_level_array)
            .toList()[0] -> LanguageLevel.Low

        context.resources.getStringArray(R.array.languages_level_array)
            .toList()[1] -> LanguageLevel.Intermediate

        context.resources.getStringArray(R.array.languages_level_array)
            .toList()[2] -> LanguageLevel.Advanced

        context.resources.getStringArray(R.array.languages_level_array)
            .toList()[3] -> LanguageLevel.Native

        else -> {
            throw CustomError("Can not convert $text to a language level")
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddLanguagePreview() {
    AddLanguage(rememberNavController())
}