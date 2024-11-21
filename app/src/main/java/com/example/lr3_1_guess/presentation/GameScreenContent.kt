package com.example.lr3_1_guess.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import kotlinx.coroutines.delay

@Composable
fun GameScreenContent(
    modifier: Modifier = Modifier,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        LoadingScreen(
            modifier = modifier
        )
    } else {
        val index by viewModel.imageIndex.collectAsState()
        val answerResult by viewModel.answerResult.collectAsState()
        if (answerResult == true) {
            viewModel.chooseImages()
        }
        GameScreen(
            imageUrl = viewModel.gameImages.first[index],
            onAnswerClick = viewModel::checkAnswer,
            answerResult = answerResult,
            onAnswerResultClear = viewModel::clearAnswerResult,
            modifier = modifier
        )
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Loading",
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun GameScreen(
    imageUrl: String,
    onAnswerClick: (String) -> Unit,
    answerResult: Boolean?,
    onAnswerResultClear: () -> Unit,
    modifier: Modifier = Modifier
) {
    var answer by rememberSaveable {
        mutableStateOf("")
    }
    var snackbarText by rememberSaveable {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    var refreshButtonPressed by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(answerResult) {
        answerResult?.let {
            snackbarText = if (it) "Правильно!" else "Неправильно :("
            delay(2000)
            snackbarText = ""
            onAnswerResultClear()
        }
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Text(
                "Игра Угадай звезду",
                fontSize = 24.sp
            )

            Spacer(
                modifier = modifier
                    .weight(0.1f)
                    .height(2.dp))

            Text(
                "Для правильного ответа необходимо указать псевдоним и/или полное имя и фамилию артиста",
                fontSize = 18.sp,
                textAlign = TextAlign.Justify
            )

            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Image to guess",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .weight(1f)
                    .padding(40.dp)
                    .fillMaxSize()
            )

            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                singleLine = true,
                label = { Text("Введите свой ответ") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = { onAnswerClick(answer) }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Blue,
                    focusedBorderColor = Color.Blue,
                    focusedLabelColor = Color.Blue
                )
            )

            Button(
                onClick = {
                    onAnswerClick(answer)
                    keyboardController?.hide()
                    answer = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text(
                    text = "Ответить",
                    textAlign = TextAlign.Center
                )
            }

//            Button(
//                onClick = {
//                    refreshButtonPressed = true
//                },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Blue
//                )
//            ) {
//                Text(
//                    text = "Обновить",
//                    textAlign = TextAlign.Center
//                )
//            }




        }
        if (snackbarText.isNotEmpty()) {
            Snackbar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp)
            ) {
                Text(
                    text = snackbarText,
                )
            }
        }

    }


//    if (refreshButtonPressed){
//        GameScreenContent(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(0.dp)
//        )
//        refreshButtonPressed = false
//    }


}

