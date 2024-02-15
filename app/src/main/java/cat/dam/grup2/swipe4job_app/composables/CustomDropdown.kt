package cat.dam.grup2.swipe4job_app.composables

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    placeholder: String = "",
    items: List<String>,
    onChange: (item: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItemIndex by remember { mutableStateOf(0) }
    var selectedItem by remember { mutableStateOf(placeholder) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }) {


        OutlinedTextField(
            value = selectedItem,
            onValueChange = { },
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                        )
                    },
                    onClick = {
                        selectedItemIndex = index
                        selectedItem = item
                        expanded = false
                        onChange(selectedItem)
                    }
                )
            }
        }
    }
}