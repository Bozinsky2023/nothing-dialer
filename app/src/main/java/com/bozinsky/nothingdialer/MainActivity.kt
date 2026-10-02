package com.bozinsky.nothingdialer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bozinsky.nothingdialer.ui.theme.NothingAccent
import com.bozinsky.nothingdialer.ui.theme.NothingBlack
import com.bozinsky.nothingdialer.ui.theme.NothingDialerTheme
import com.bozinsky.nothingdialer.ui.theme.NothingSecondary
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

enum class DialerTab {
    Keypad,
    Recent,
    Favorites
}

data class ContactEntry(
    val name: String,
    val phone: String,
    val initials: String,
    val favorite: Boolean = false
)

data class RecentCallItem(
    val name: String,
    val number: String,
    val time: String,
    val type: String,
    val accent: Color = NothingAccent
)

@Composable
fun NothingDialerApp() {
    val context = LocalContext.current
    var dialText by remember { mutableStateOf("") }
    var selectedTab by remember { mutableStateOf(DialerTab.Keypad) }

    val favoriteContacts = remember {
        listOf(
            ContactEntry("Mom", "+1 (650) 340-5811", "M", true),
            ContactEntry("Alice Chen", "+1 (415) 820-1998", "A", true),
            ContactEntry("Sam Patel", "+1 (303) 671-9085", "S", true),
            ContactEntry("Nexus Studio", "+1 (212) 442-7800", "N", false)
        )
    }

    val recentCalls = remember {
        listOf(
            RecentCallItem("Alice Chen", "+1 (415) 820-1998", "12 min ago", "Outgoing", NothingAccent),
            RecentCallItem("Mom", "+1 (650) 340-5811", "Today", "Incoming", Color(0xFF8FD5FF)),
            RecentCallItem("Nexus Studio", "+1 (212) 442-7800", "Missed", "Missed", Color(0xFFFF8C7B)),
            RecentCallItem("Sam Patel", "+1 (303) 671-9085", "Yesterday", "Outgoing", NothingAccent)
        )
    }

    val keypadRows = remember {
        listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("*", "0", "#")
        )
    }

    fun handleDialIntent() {
        val cleanNumber = dialText.filter { it.isDigit() || it == '+' || it == '*' || it == '#' }
        if (cleanNumber.isBlank()) return

        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.fromParts("tel", cleanNumber, null)
        }
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NothingBlack)
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HeaderBar()

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DialerTab.entries.forEach { tab ->
                val selected = selectedTab == tab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (selected) NothingAccent else NothingSurface)
                        .clickable { selectedTab = tab }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.name,
                        color = if (selected) Color.Black else Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        when (selectedTab) {
            DialerTab.Keypad -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(76.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(NothingSurface)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = dialText.ifBlank { "Enter number" },
                            color = if (dialText.isBlank()) NothingSecondary else Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    keypadRows.forEach { row ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            row.forEach { value ->
                                DialPadButton(
                                    value = value,
                                    onClick = { dialText += value }
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(78.dp)
                                .clip(CircleShape)
                                .background(NothingSurface)
                                .clickable { dialText = dialText.dropLast(1) }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(NothingAccent)
                                .clickable { handleDialIntent() }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Dial",
                                tint = Color.Black,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }

            DialerTab.Recent -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(recentCalls) { item ->
                        RecentCallRow(item = item)
                    }
                }
            }

            DialerTab.Favorites -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    favoriteContacts.forEach { contact ->
                        FavoriteContactRow(
                            contact = contact,
                            onTap = { dialText = contact.phone }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HeaderBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Nothing",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Dialer",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier
                .size(46.dp)
                .clip(CircleShape)
                .background(NothingSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Status",
                tint = NothingAccent,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun DialPadButton(value: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .clip(CircleShape)
            .background(NothingSurface)
            .clickable { onClick() }
            .border(1.dp, Color(0x1FFFFFFF), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = value,
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )
            if (value in listOf("2", "3", "4", "5", "6", "7", "8", "9")) {
                Text(
                    text = when (value) {
                        "2" -> "ABC"
                        "3" -> "DEF"
                        "4" -> "GHI"
                        "5" -> "JKL"
                        "6" -> "MNO"
                        "7" -> "PQRS"
                        "8" -> "TUV"
                        else -> "WXYZ"
                    },
                    color = Color(0xFF8E8E8E),
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
private fun RecentCallRow(item: RecentCallItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(NothingSurface)
            .padding(horizontal = 14.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(NothingTertiary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                Text(
                    text = item.number,
                    color = Color(0xFFBDBDBD),
                    fontSize = 12.sp
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = item.time,
                color = Color.White,
                fontSize = 12.sp
            )
            Text(
                text = item.type,
                color = item.accent,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun FavoriteContactRow(contact: ContactEntry, onTap: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(NothingSurface)
            .clickable { onTap() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(NothingTertiary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.initials,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Column {
                Text(
                    text = contact.name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = contact.phone,
                    color = Color(0xFFBDBDBD),
                    fontSize = 12.sp
                )
            }
        }

        if (contact.favorite) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Favorite",
                tint = NothingAccent,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Contact",
                tint = Color(0xFFBDBDBD),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
