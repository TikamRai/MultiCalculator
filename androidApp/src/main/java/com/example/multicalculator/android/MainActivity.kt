package com.example.multicalculator.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalcView();
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
        CalcView();
    }
}

@Composable
fun CalcView() {
    var leftNumber = rememberSaveable { mutableStateOf(0) }
    var rightNumber = rememberSaveable { mutableStateOf(0) }
    var operation = rememberSaveable { mutableStateOf("") }
    var complete = rememberSaveable { mutableStateOf(false) }
    if (complete.value && operation.value !== "") {
        var answer = 0
        answer = when (operation.value) {
            "+" -> leftNumber.value + rightNumber.value
            "-" -> leftNumber.value - rightNumber.value
            "*" -> leftNumber.value * rightNumber.value
            "/" -> leftNumber.value / rightNumber.value
            else -> 0
        }
        var displayText = answer.toString()
    } else if (!complete.value && operation.value !== "") {
        var displayText = rightNumber.value
    } else {
        var displayText = leftNumber.value
    }

    var displayText = remember { mutableStateOf("0") }
        Column(modifier = Modifier.background(Color.LightGray)) {
            Row(modifier = Modifier.padding(10.dp)) {
                CalcDisplay(displayText)
            }
            Row() {
                Column() {
                    for (i in 7 downTo 1 step 3) {
                        CalcRow(displayText, i, 3)
                    }
                    Row() {
                        CalcNumericButton(number = 0, display = displayText)
                        CalcEqualsButton(displayText)
                    }
                }
                Column() {
                    CalcOperationButton(operation = "+", display = displayText);
                    CalcOperationButton(operation = "–", display = displayText);
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
    Text(text = display.value, fontSize = 20.sp,
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxHeight())
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(90.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {display.value += number.toString()}) {
        Text(text = number.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(90.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { /*TODO*/ }) {
        Text(text = operation, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(90.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { val display = 0 }) {
        Text(text = "=", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}
