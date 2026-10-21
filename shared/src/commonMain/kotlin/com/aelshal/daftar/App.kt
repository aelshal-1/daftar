package com.aelshal.daftar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Title = TextStyle(color = DaftarColors.Ink, fontSize = 28.sp, fontWeight = FontWeight.Bold)
private val Subtitle = TextStyle(color = DaftarColors.InkQuiet, fontSize = 15.sp)
private val SectionTitle = TextStyle(color = DaftarColors.Ink, fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
private val Label = TextStyle(color = DaftarColors.InkQuiet, fontSize = 13.sp)
private val Value = TextStyle(color = DaftarColors.Ink, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)

/** Phase 0 home screen: proves the app builds and runs on every platform. */
@Composable
fun App() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DaftarColors.Ground)
            .safeDrawingPadding(),
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            BilingualRow(text = Bi("Daftar", "دفتر"), style = Title, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(4.dp))
            BilingualRow(
                text = Bi("Your family's health file", "ملف صحة العيلة"),
                style = Subtitle,
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            DaftarColors.Tabs.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(width = 52.dp, height = 28.dp)
                        .background(color, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(DaftarColors.Sheet, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            BilingualRow(
                text = Bi("Setup check", "فحص التجهيز"),
                style = SectionTitle,
                modifier = Modifier.fillMaxWidth(),
            )
            InfoRow(Bi("Device", "الجهاز"), platformName())
            InfoRow(Bi("Version", "الإصدار"), APP_VERSION)
            InfoRow(Bi("Family data", "بيانات العيلة"), "Not connected yet  ·  لسه مش متوصلة")

            Spacer(Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DaftarColors.AccentSoft, RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center,
            ) {
                BilingualRow(
                    text = Bi("Private, family only", "خاص للعيلة بس"),
                    style = Label.copy(color = DaftarColors.Accent, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun InfoRow(label: Bi, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        BilingualRow(text = label, style = Label, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(2.dp))
        BasicText(text = value, style = Value)
        Spacer(Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(DaftarColors.Rule),
        )
    }
}
