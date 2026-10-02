package com.bozinsky.nothingdialer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bozinsky.nothingdialer.ui.theme.NothingAccent
import com.bozinsky.nothingdialer.ui.theme.NothingBlack
import com.bozinsky.nothingdialer.ui.theme.NothingDialerTheme
import com.bozinsky.nothingdialer.ui.theme.NothingSurface
import com.bozinsky.nothingdialer.ui.theme.NothingTertiary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NothingDialerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NothingDialerApp()
                }
            }
        }
    }
}

data class CallLogItem(
    val name: String,
    val number: String,
    val duration: String,
    val type: String
)

@Composable
fun NothingDialerApp() {
    val recentCalls = listOf(
        CallLogItem("Alice Chen", "+1 (415) 820-1998", "12 min", "Outgoing"),
        CallLogItem("Mom", "+1 (650) 340-5811", "5 min", "Incoming"),
        CallLogItem("Nexus Studio", "+1 (212) 442-7800", "32 min", "Missed"),
        CallLogItem("Sam Patel", "+1 (303) 671-9085", "8 min", "Outgoing")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NothingBlack)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Nothing",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(NothingSurface),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Status",
                    tint = NothingAccent,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Dialer",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Contacts + call history",
                    color = Color(0xFFB7B7B7),
                    fontSize = 14.sp
                )
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(NothingAccent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Call",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        val keypad = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("*", "0", "#")
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            keypad.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { value ->
                        DialPadButton(value = value)
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(NothingSurface),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+1 (415) 820-1998",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1D1D1D)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Backspace",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(NothingAccent),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "Dial",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Text(
            text = "Recent",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(recentCalls) { call ->
                RecentCallItem(call = call)
            }
        }
    }
}

@Composable
fun DialPadButton(value: String) {
    Box(
        modifier = Modifier
            .size(86.dp)
            .clip(CircleShape)
            .background(NothingSurface)
            .clickable { }
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (value == "1" || value == "2" || value == "3" || value == "4" || value == "5" || value == "6" || value == "7" || value == "8" || value == "9" || value == "0") {
                Text(
                    text = when (value) {
                        "1" -> ""
                        "2" -> "ABC"
                        "3" -> "DEF"
                        "4" -> "GHI"
                        "5" -> "JKL"
                        "6" -> "MNO"
                        "7" -> "PQRS"
                        "8" -> "TUV"
                        "9" -> "WXYZ"
                        else -> "+"
                    },
                    color = Color(0xFFB7B7B7),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun RecentCallItem(call: CallLogItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(NothingSurface)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(NothingTertiary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = call.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = call.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = call.number,
                    color = Color(0xFFB7B7B7),
                    fontSize = 12.sp
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = call.duration,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = call.type,
                color = NothingAccent,
                fontSize = 11.sp
            )
        }
    }
}
