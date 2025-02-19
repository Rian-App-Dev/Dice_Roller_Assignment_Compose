package org.example.mydiceroller

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mydice.composeapp.generated.resources.Res
import mydice.composeapp.generated.resources.dice_1
import mydice.composeapp.generated.resources.dice_2
import mydice.composeapp.generated.resources.dice_3
import mydice.composeapp.generated.resources.dice_4
import mydice.composeapp.generated.resources.dice_5
import mydice.composeapp.generated.resources.dice_6
import mydice.composeapp.generated.resources.img
import mydice.composeapp.generated.resources.intro
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val introText = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue)) {
                append("Welcome to the game!\n")
            }
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("Loser will be eliminated")
            }
        }
        val isPlayer1 = remember { mutableStateOf(true) }
        val playerScores = remember { mutableStateOf(Array(2) { 0 }) }
        val diceImages = remember {
            listOf(
                Res.drawable.dice_1,
                Res.drawable.dice_2,
                Res.drawable.dice_3,
                Res.drawable.dice_4,
                Res.drawable.dice_5,
                Res.drawable.dice_6
            )
        }
        val currentDiceImage = remember { mutableStateOf(Res.drawable.intro) }
        var result = ""
        if (playerScores.value[0] >= 50 || playerScores.value[1] >= 50) {
            if (playerScores.value[0] >= 50) {
                result = "Player 1 is winner"
                currentDiceImage.value = Res.drawable.img
                if (playerScores.value[1] > playerScores.value[0]){
                    result = "Player 2 is winner"
                }
            } else {
                result = "Player 2 is winner"
                currentDiceImage.value = Res.drawable.img
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = introText,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.Red,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(currentDiceImage.value),
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .size(350.dp)
                )
                OutlinedButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        val randomNumber = (1..6).random()
                        currentDiceImage.value = diceImages[randomNumber - 1]
                        if (isPlayer1.value) {
                            playerScores.value[0] += randomNumber
                        } else {
                            playerScores.value[1] += randomNumber
                        }
                        isPlayer1.value = !isPlayer1.value
                    }
                ) {
                    if (isPlayer1.value)
                        Text(
                            text = "Roll the dice for player 1",
                            color = Color.Black
                        )
                    else
                        Text(
                            text = "Roll the dice for player 2",
                            color = Color.Red
                        )
                }
                Row(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.Black)) {
                                append("Player 1\n")
                            }
                            withStyle(SpanStyle(color = Color.Green, fontSize = 40.sp)) {
                                append("\n${playerScores.value[0]}")
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = Color.Black)) {
                                append("Player 2\n")
                            }
                            withStyle(SpanStyle(color = Color.Green, fontSize = 40.sp)) {
                                append("\n${playerScores.value[1]}")
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                }
                IconButton(
                    onClick = {
                        playerScores.value = Array(2) { 0 }
                        currentDiceImage.value = Res.drawable.intro
                        isPlayer1.value = true
                        result = ""
                    },
                    modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Text(
                text = result, fontSize = 30.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}