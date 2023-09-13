package com.example.samplemvvmcar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.samplemvvmcar.ui.theme.SampleMVVMCarTheme

data class Car(val brand: String)

class CarViewModel : ViewModel() {
    private val carList = mutableListOf<Car>()

    fun getCars(): List<Car> {
        return carList
    }

    fun addCar(car: Car) {
        carList.add(car)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarListScreen(carViewModel: CarViewModel = viewModel()) {
    var newCarBrand by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Car List", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Display the list of cars
        Column {
            carViewModel.getCars().forEach { car ->
                Text(text = car.brand, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Add a new car
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newCarBrand,
                onValueChange = {
                    newCarBrand = it
                },
                textStyle = TextStyle(fontSize = 18.sp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )

            Button(
                onClick = {
                    val newCar = Car(newCarBrand.text)
                    carViewModel.addCar(newCar)
                    newCarBrand = TextFieldValue()
                }
            ) {
                Text(text = "Add Car")
            }
        }
    }
}



class MainActivity : ComponentActivity() {

    private val carViewModel: CarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleMVVMCarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CarListScreen(carViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SampleMVVMCarTheme {
        Greeting("Android")
    }
}