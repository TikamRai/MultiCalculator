package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.multicalculator.Greeting
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}

@Composable
fun CalcView() {
    val displayText = remember { mutableStateOf("0") }
    Column(modifier = Modifier.background(Color.LightGray)){
        Row() {
            CalcDisplay(displayText)
        }
        Row() {
            Column() {
                for (i in 7 downTo  1 step 3) {
                    CalcRow(displayText, i, 3)
                }
            }
            Column() {
                CalcOperationButton(operation = "+", display = displayText);
                CalcOperationButton(operation = "-", display = displayText);
                CalcOperationButton(operation = "x", display = displayText);
                CalcOperationButton(operation = "÷", display = displayText);
            }
        }
    }
}

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
    val endNum = startNum +numButtons;
    Row (modifier = Modifier.padding(0.dp)) {
        for (num in startNum until endNum){
            CalcNumericButton(number = num, display = display)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(text = display.toString(),
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxHeight())
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>) {
    ElevatedButton(modifier = Modifier.padding(4.dp),
        onClick = {display.value += number.toString()}) {
        Text(text = number.toString())
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {
    ElevatedButton(modifier = Modifier.padding(4.dp),
        onClick = { /*TODO*/ }) {
        Text(text = operation)
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>) {
    ElevatedButton(modifier = Modifier.padding(4.dp),
        onClick = { val display = 0 }) {
        Text(text = "=")
    }
}
