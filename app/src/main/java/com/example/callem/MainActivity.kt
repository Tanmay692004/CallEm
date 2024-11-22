package com.example.callem

import androidx.compose.foundation.layout.fillMaxSize
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.callem.ui.theme.CallEmTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val emergencyNumber = "tel:+919999999999" // to be replaced
    private val emergencyContacts = listOf(
        "+918888888888", // To Be Replaced with real emergency contacts
        "+9101125532553",
        "+919999999999"
    )

    private val callPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            makeEmergencyCall()
            sendEmergencyMessages()
        } else {
            Toast.makeText(this, "Call permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    private val smsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            Toast.makeText(this, "SMS permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request SMS permission immediately (if not already granted)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            smsPermissionLauncher.launch(Manifest.permission.SEND_SMS)
        }

        setContent {
            CallEmTheme {
                MainScreen(
                    onTaskCompleted = {
                        makeEmergencyCallAndSendMessages()
                    },
                    onCloseApp = {
                        finishAffinity() // Closes the app
                    }
                )
            }
        }
    }

    private fun makeEmergencyCallAndSendMessages() {
        // Check and request CALL_PHONE permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            makeEmergencyCall()
            sendEmergencyMessages()
        } else {
            callPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
        }
    }

    private fun makeEmergencyCall() {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = Uri.parse(emergencyNumber)
        }
        try {
            startActivity(callIntent)
        } catch (e: SecurityException) {
            Toast.makeText(this, "Call permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmergencyMessages() {
        lifecycleScope.launch {
            try {
                val smsManager = SmsManager.getDefault()
                val message = "just a test message, sorry for the inconvenience"

                // Send SMS to all emergency contacts
                for (contact in emergencyContacts) {
                    smsManager.sendTextMessage(contact, null, message, null, null)
                }

                Toast.makeText(this@MainActivity, "Emergency messages sent successfully!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Failed to send messages: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun MainScreen(onTaskCompleted: () -> Unit, onCloseApp: () -> Unit) {
    var showText by remember { mutableStateOf(false) }

    // Trigger the task
    LaunchedEffect(Unit) {
        onTaskCompleted() // Call and message task
        showText = true

        // Delay for 2 seconds, then close the app
        delay(5000)
        onCloseApp()
    }

    // Centered UI
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (showText) {
            Text(text = "Called 'Em", style = MaterialTheme.typography.headlineMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CallEmTheme {
        MainScreen(onTaskCompleted = {}, onCloseApp = {})
    }
}
