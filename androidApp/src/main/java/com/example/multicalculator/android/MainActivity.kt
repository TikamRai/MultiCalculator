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
    var displayText = remember { mutableStateOf("0")}
    var leftNumber = rememberSaveable { mutableStateOf(0) }
    var rightNumber = rememberSaveable { mutableStateOf(0) }
    var operation = rememberSaveable { mutableStateOf("") }
    var complete = rememberSaveable { mutableStateOf(false) }

    if (complete.value && operation.value.isNotEmpty()) {
        var answer = 0
        when (operation.value) {
            "+" -> answer = leftNumber.value + rightNumber.value
            "–" -> answer = leftNumber.value - rightNumber.value
            "x" -> answer = leftNumber.value * rightNumber.value
            "÷" -> answer = leftNumber.value / rightNumber.value
        }
        displayText.value = answer.toString()
    } else if (operation.value.isNotEmpty() && !complete.value) {
        displayText.value = rightNumber.value.toString()
    } else {
        displayText.value = leftNumber.value.toString()
    }

    fun numberPress(btnNum: Int) {
        if (complete.value) {
            leftNumber.value = 0
            rightNumber.value = 0
            operation.value = ""
            complete.value = false
        }

        if (operation.value.isNotEmpty() && !complete.value) {
            rightNumber.value = (rightNumber.value * 10) + btnNum
        } else if (operation.value.isEmpty() && !complete.value) {
            leftNumber.value = (leftNumber.value * 10) + btnNum
        }
    }

    fun operationPress(op: String) {
        if (!complete.value) {
            operation.value = op
        }
    }

    fun equalsPress() {
        complete.value = true
    }

    fun clearPress() {
        leftNumber.value = 0
        rightNumber.value = 0
        operation.value = ""
        complete.value = false
    }

    Column(modifier = Modifier.background(Color.LightGray)) {
        Row(modifier = Modifier.padding(10.dp)) {
            CalcDisplay(displayText)
        }
        Row() {
            Column() {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(onPress = {number -> numberPress(number)}, i, 3)
                }
                Row() {
                    CalcNumericButton(number = 0, onPress = {number -> numberPress(number)})
                    CalcEqualsButton(onPress = {equalsPress()})
                    CalcClrButton (onPress = { clearPress()})
                }
            }
            Column() {
                CalcOperationButton(operation = "+", onPress = { operation -> operationPress(operation) });
                CalcOperationButton(operation = "–", onPress = { operation -> operationPress(operation) });
                CalcOperationButton(operation = "x", onPress = { operation -> operationPress(operation) });
                CalcOperationButton(operation = "÷", onPress = { operation -> operationPress(operation) });
            }
        }
    }
}

@Composable
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum +numButtons;
    Row (modifier = Modifier.padding(0.dp)) {
        for (num in startNum until endNum){
            CalcNumericButton(number = num, onPress = onPress)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(text = display.value, fontSize = 60.sp,
        modifier = Modifier
            .height(410.dp)
            .padding(5.dp)
            .fillMaxHeight())
}

@Composable
fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(95.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = {onPress(number)}) {
        Text(text = number.toString(), fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(95.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onPress(operation)}) {
        Text(text = operation, fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun CalcEqualsButton(onPress: () -> Unit) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(95.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onPress() }) {
        Text(text = "=", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}

@Composable
fun CalcClrButton(onPress: () -> Unit) {
    ElevatedButton(modifier = Modifier
        .padding(4.dp)
        .size(95.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = { onPress()}) {
        Text(text = "C", fontSize = 40.sp, fontWeight = FontWeight.ExtraBold)
    }
}
