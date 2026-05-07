package com.comind.myefreicard.data

data class Student(
    val firstName: String,
    val lastName: String,
    val program: String,
    val studentId: String,
    val year: String,
    val ine: String,
    val email: String,
    val phone: String,
    val address: String,
    val dateOfBirth: String,
    val gpa: String,
    val major: String,
    val minor: String,
    val enrollmentDate: String,
    val expectedGraduation: String,
    val validUntil: String,
    val status: String,
    val emergencyContactName: String,
    val emergencyContactRelation: String,
    val emergencyContactPhone: String
)

data class Course(
    val code: String,
    val name: String,
    val professor: String,
    val time: String,
    val location: String,
    val days: String,
    val credits: Int
)

data class Facility(
    val name: String,
    val type: String,
    val isActive: Boolean,
    val hours: String,
    val iconType: FacilityIcon
)

enum class FacilityIcon {
    LIBRARY, GYM, DINING, LAB, HEALTH, AUDITORIUM
}

object SampleData {
    val student = Student(
        firstName = "Emma",
        lastName = "Johnson",
        program = "PGE",
        studentId = "2024-ST-15892",
        year = "I-1 2022 - 2027",
        ine = "1234567890A",
        email = "emma.johnson@university.edu",
        phone = "+1 (555) 123-4567",
        address = "123 Campus Drive, Apt 4B\nBoston, MA 02115",
        dateOfBirth = "June 15, 2003",
        gpa = "3.78 / 4.0",
        major = "Computer Science",
        minor = "Mathematics",
        enrollmentDate = "Sep 1, 2022",
        expectedGraduation = "2026-05",
        validUntil = "2026-05",
        status = "Active",
        emergencyContactName = "Michael Johnson",
        emergencyContactRelation = "Father",
        emergencyContactPhone = "+1 (555) 987-6543"
    )

    val courses = listOf(
        Course("CS 301", "Data Structures", "Dr. Sarah Chen", "9:00 AM - 10:15 AM", "Engineering Hall 205", "MWF", 4),
        Course("CS 340", "Database Systems", "Prof. James Martinez", "1:00 PM - 2:30 PM", "Tech Center 112", "TTh", 3),
        Course("MATH 250", "Linear Algebra", "Dr. Lisa Wang", "11:00 AM - 12:15 PM", "Math Building 301", "MWF", 3),
        Course("CS 355", "Software Engineering", "Dr. Robert Kim", "3:00 PM - 4:15 PM", "Engineering Hall 110", "TTh", 3),
        Course("ENG 201", "Technical Writing", "Prof. Maria Garcia", "2:00 PM - 2:50 PM", "Humanities 205", "MWF", 4)
    )

    val facilities = listOf(
        Facility("Main Library", "Library", true, "24/7", FacilityIcon.LIBRARY),
        Facility("Recreation Center", "Gym", true, "6 AM - 11 PM", FacilityIcon.GYM),
        Facility("Student Union", "Dining", true, "7 AM - 10 PM", FacilityIcon.DINING),
        Facility("Computer Lab - Tech Building", "Lab", true, "24/7", FacilityIcon.LAB),
        Facility("Health Center", "Health", true, "8 AM - 6 PM", FacilityIcon.HEALTH),
        Facility("Main Auditorium", "Auditorium", true, "8 AM - 10 PM", FacilityIcon.AUDITORIUM)
    )

    val semester = "Spring 2026 Semester"
    val totalCredits = courses.sumOf { it.credits }
    val totalCourses = courses.size
}
