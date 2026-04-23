package ni.edu.uam.apphabitos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ni.edu.uam.apphabitos.ui.theme.AppHabitosTheme

data class Habito(
    val nombre: String,
    val meta: String,
    val categoria: String,
    val completado: Boolean = false
)

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppHabitosTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DashboardHabitosScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardHabitosScreen() {
    var nombreHabito by remember { mutableStateOf("") }
    var metaHabito by remember { mutableStateOf("") }
    var categoriaHabito by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Agrega un hábito para comenzar") }

    val habitos = remember {
        mutableStateListOf<Habito>()
    }

    val completados = habitos.count { it.completado }
    val progreso = if (habitos.isNotEmpty()) completados.toFloat() / habitos.size else 0f
    val porcentaje = (progreso * 100).toInt()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dashboard de Hábitos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar hábito")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // Sección 1: Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hola, Gabriel",
                    style = MaterialTheme.typography.headlineSmall
                )
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificaciones"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sección 2: Tarjeta de progreso
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Progreso de hoy",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.LightGray, RoundedCornerShape(10.dp))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progreso)
                                .height(20.dp)
                                .background(Color(0xFF4CAF50), RoundedCornerShape(10.dp))
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "$porcentaje%",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color(0xFF2E7D32)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Formulario para agregar hábitos
            Text(
                text = "Agregar nuevo hábito",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = nombreHabito,
                onValueChange = { nombreHabito = it },
                label = { Text("Nombre del hábito") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = metaHabito,
                onValueChange = { metaHabito = it },
                label = { Text("Meta u hora") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = categoriaHabito,
                onValueChange = { categoriaHabito = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (nombreHabito.isNotBlank() && metaHabito.isNotBlank() && categoriaHabito.isNotBlank()) {
                        habitos.add(
                            Habito(
                                nombre = nombreHabito,
                                meta = metaHabito,
                                categoria = categoriaHabito,
                                completado = false
                            )
                        )
                        mensaje = "Hábito agregado correctamente"
                        nombreHabito = ""
                        metaHabito = ""
                        categoriaHabito = ""
                    } else {
                        mensaje = "Completa todos los campos"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar hábito")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Sección 3: Lista de hábitos
            Text(
                text = "Hábitos de hoy",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (habitos.isEmpty()) {
                Text(
                    text = "Todavía no has agregado hábitos",
                    color = Color.Gray
                )
            } else {
                Column {
                    habitos.forEachIndexed { index, habito ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    habitos[index] = habito.copy(
                                        completado = !habito.completado
                                    )
                                },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if (habito.completado)
                                        Icons.Default.CheckCircle
                                    else
                                        Icons.Default.RadioButtonUnchecked,
                                    contentDescription = "Estado",
                                    tint = if (habito.completado) Color(0xFF4CAF50) else Color.Gray
                                )

                                Spacer(modifier = Modifier.size(8.dp))

                                Column {
                                    Text(
                                        text = habito.nombre,
                                        color = if (habito.completado) Color(0xFF2E7D32) else Color.Black
                                    )
                                    Text(
                                        text = "${habito.meta} • ${habito.categoria}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sección 4: Resumen semanal
            Text(
                text = "Resumen semanal",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val dias = listOf(
                    "L" to true,
                    "M" to true,
                    "X" to false,
                    "J" to true,
                    "V" to false,
                    "S" to true,
                    "D" to false
                )

                dias.forEach { (dia, completo) ->
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = if (completo) Color(0xFF4CAF50) else Color.LightGray,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = dia,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardHabitosPreview() {
    AppHabitosTheme {
        DashboardHabitosScreen()
    }
}