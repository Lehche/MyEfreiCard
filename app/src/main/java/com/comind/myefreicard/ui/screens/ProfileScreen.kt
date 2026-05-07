package com.comind.myefreicard.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comind.myefreicard.data.SampleData
import com.comind.myefreicard.ui.theme.*

@Composable
fun ProfileScreen() {
    val student = SampleData.student
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundLight)
            .verticalScroll(scrollState)
            .padding(bottom = 100.dp)
    ) {
        // Title
        Text(
            text = "Student Profile",
            style = MaterialTheme.typography.headlineLarge,
            color = TextPrimary,
            modifier = Modifier.padding(start = 20.dp, top = 16.dp, bottom = 16.dp)
        )

        // Profile Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .shadow(12.dp, RoundedCornerShape(20.dp), ambientColor = PrimaryBlue.copy(alpha = 0.2f)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box {
                // Gradient background
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(GradientStart, GradientEnd),
                                start = Offset(0f, 0f),
                                end = Offset(Float.POSITIVE_INFINITY, 0f)
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 50.dp)
                        .background(Color.Transparent)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(SurfaceWhite)
                            .padding(3.dp)
                            .clip(CircleShape)
                            .background(PrimaryBlue.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 32.sp)
                    }

                    // Name and major on white bg
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(SurfaceWhite)
                            .padding(horizontal = 24.dp)
                            .padding(top = 8.dp, bottom = 20.dp)
                    ) {
                        Text(
                            text = "${student.firstName} ${student.lastName}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = student.major,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Contact Information
        SectionCard(title = "Contact Information") {
            ProfileInfoRow(Icons.Outlined.Email, "Email", student.email)
            Spacer(modifier = Modifier.height(16.dp))
            ProfileInfoRow(Icons.Outlined.Phone, "Phone", student.phone)
            Spacer(modifier = Modifier.height(16.dp))
            ProfileInfoRow(Icons.Outlined.LocationOn, "Address", student.address)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Academic Information
        SectionCard(title = "Academic Information") {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    AcademicField(Icons.Outlined.Badge, "Student ID", student.studentId)
                }
                Column(modifier = Modifier.weight(1f)) {
                    AcademicField(Icons.Outlined.School, "Year", "Junior")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    AcademicField(Icons.Outlined.CalendarToday, "Date of Birth", student.dateOfBirth)
                }
                Column(modifier = Modifier.weight(1f)) {
                    AcademicField(null, "GPA", student.gpa)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    AcademicFieldText("Major", student.major)
                }
                Column(modifier = Modifier.weight(1f)) {
                    AcademicFieldText("Minor", student.minor)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    AcademicFieldText("Enrollment Date", student.enrollmentDate)
                }
                Column(modifier = Modifier.weight(1f)) {
                    AcademicFieldText("Expected Graduation", student.expectedGraduation)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Emergency Contact
        SectionCard(title = "Emergency Contact") {
            AcademicFieldText("Name", student.emergencyContactName)
            Spacer(modifier = Modifier.height(16.dp))
            AcademicFieldText("Relationship", student.emergencyContactRelation)
            Spacer(modifier = Modifier.height(16.dp))
            AcademicFieldText("Phone", student.emergencyContactPhone)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .shadow(4.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            content()
        }
    }
}

@Composable
private fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = TextSecondary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = PrimaryBlue.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AcademicField(icon: ImageVector?, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = TextSecondary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = PrimaryBlue.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun AcademicFieldText(label: String, value: String) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = PrimaryBlue.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            fontWeight = FontWeight.SemiBold
        )
    }
}
