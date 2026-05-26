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
    val moyenneGenerale: String,
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
    val campus: String,
    val building: String,
    val type: String,
    val openHour: Int,    // hour in 24h Paris time
    val openMinute: Int,
    val closeHour: Int,
    val closeMinute: Int,
    val iconType: FacilityIcon
)

enum class FacilityIcon {
    GYM, DANCE, DINING, LAB, HUB, CAFE
}

object SampleData {
    val student = Student(
        firstName = "Gaspard",
        lastName = "Dupont",
        program = "Prépa Intégrée",
        studentId = "20241122",
        year = "2ème année - Prépa",
        ine = "1234567890A",
        email = "gaspard.dupont@efrei.net",
        phone = "+33 6 11 22 33 44",
        address = "12 rue Linné\n75005 Paris",
        dateOfBirth = "15/06/2003",
        moyenneGenerale = "15/20",
        enrollmentDate = "1 sept. 2024",
        expectedGraduation = "05/2029",
        validUntil = "05/2029",
        status = "Active",
        emergencyContactName = "Marie Dupont",
        emergencyContactRelation = "Mère",
        emergencyContactPhone = "+33 6 74 85 91 32"
    )

    val courses = listOf(
        Course("SM403I-2526PSP01", "Data Analysis", "", "", "", "", 3),
        Course("SM402I-2526PSP01", "Finite Automata and Regular Expressions", "", "", "", "", 3),
        Course("SM401I-2526PSP01", "Mathematical Modeling", "", "", "", "", 3),
        Course("TI404I-2526PSP01", "Databases 1: Basic Concepts", "", "", "", "", 3),
        Course("TI403I-2526PSP01", "Java 1: Fundamentals of OOP", "", "", "", "", 3),
        Course("TI402I-2526PSP01", "Web Programming 1: HTML, CSS, JS", "", "", "", "", 3),
        Course("SP401I-2526PSP01", "Electromagnetic Propagation", "", "", "", "", 3),
        Course("SP402I-2526PSP01", "Thermodynamics", "", "", "", "", 3),
        Course("TE403I-2526PSP01", "Transmission Channels", "", "", "", "", 3),
        Course("FH401-2526PSP01", "Démocratie et Engagement - Dissertation et plaidoyer", "", "", "", "", 2),
        Course("FE402I-2526PSP01", "Economics", "", "", "", "", 2),
        Course("FL401-2526PSP01", "English 4 - Preparation for the Study Abroad Program", "", "", "", "", 2)
    )

    // All campuses / facilities
    // openHour/closeHour are Paris local time (24h)
    val facilities = listOf(
        Facility(
            name = "Gym",
            campus = "Site New Republic",
            building = "Bât. N",
            type = "Sport",
            openHour = 6, openMinute = 30,
            closeHour = 21, closeMinute = 30,
            iconType = FacilityIcon.GYM
        ),
        Facility(
            name = "Dance Room",
            campus = "Site New Republic",
            building = "Bât. N",
            type = "Sport",
            openHour = 6, openMinute = 30,
            closeHour = 21, closeMinute = 30,
            iconType = FacilityIcon.DANCE
        ),
        Facility(
            name = "Crous",
            campus = "Site La Maison",
            building = "Bât. A (Sous-sol)",
            type = "Restauration",
            openHour = 10, openMinute = 30,
            closeHour = 15, closeMinute = 30,
            iconType = FacilityIcon.DINING
        ),
        Facility(
            name = "Innovation Lab",
            campus = "Site La Maison",
            building = "Bât. I",
            type = "Laboratoire",
            openHour = 7, openMinute = 30,
            closeHour = 20, closeMinute = 0,
            iconType = FacilityIcon.LAB
        ),
        Facility(
            name = "Student Hub",
            campus = "Site La Maison",
            building = "Bât. F",
            type = "Espace étudiant",
            openHour = 7, openMinute = 30,
            closeHour = 20, closeMinute = 0,
            iconType = FacilityIcon.HUB
        ),
        Facility(
            name = "Lunch & Espace Coworking",
            campus = "Site La Factory",
            building = "Bât. H",
            type = "Restauration & Coworking",
            openHour = 7, openMinute = 30,
            closeHour = 20, closeMinute = 0,
            iconType = FacilityIcon.DINING
        ),
        Facility(
            name = "K-Fet",
            campus = "Site La Maison",
            building = "Bât. E",
            type = "Cafétéria",
            openHour = 7, openMinute = 30,
            closeHour = 20, closeMinute = 0,
            iconType = FacilityIcon.CAFE
        )
    )

    val semester = "Semestre 2 - 2025/2026"
    val totalCredits = courses.sumOf { it.credits }
    val totalCourses = courses.size
}
