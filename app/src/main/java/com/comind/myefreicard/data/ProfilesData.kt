package com.comind.myefreicard.data

enum class AccessLevel {
    STUDENT,
    TEACHER,
    SPECIAL_AGENT,
    THE_ONE
}

data class Profile(
    val student: Student,
    val courses: List<Course>,
    val accessLevel: AccessLevel,
    val password: String,
    val avatarEmoji: String = "👤"
)

object ProfilesData {
    // 1. Gaspard Dupont (Standard Student)
    val gaspard = Profile(
        student = Student(
            firstName = "Gaspard",
            lastName = "Dupont",
            program = "M1 - Software Engineering",
            studentId = "20241122",
            year = "1ère année - Cycle Ingé",
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
        ),
        courses = listOf(
            Course("SM403I-SE01", "Data Analysis", "Prof. Alice", "08:30 - 10:00", "Bât. N - Amphi 1", "Mon, Wed", 3),
            Course("SM402I-SE02", "Finite Automata", "Prof. Bob", "10:15 - 11:45", "Bât. A - Room 204", "Mon, Thu", 3),
            Course("TI404I-SE03", "Databases 1", "Prof. Charlie", "13:30 - 15:00", "Bât. I - Lab 3", "Tue", 3),
            Course("TI403I-SE04", "Java 1: OOP", "Prof. Diana", "15:15 - 16:45", "Bât. I - Lab 4", "Wed", 3),
            Course("TI402I-SE05", "Web Dev: HTML/CSS/JS", "Prof. Gaspard", "08:30 - 10:00", "Bât. H - Coworking", "Fri", 3)
        ),
        accessLevel = AccessLevel.STUDENT,
        password = "gaspard_prepa",
        avatarEmoji = "🧑‍💻"
    )

    // 2. Ada Lovelace (Teacher & Administrator)
    val ada = Profile(
        student = Student(
            firstName = "Ada",
            lastName = "Lovelace",
            program = "Professeur de Cybersécurité & IA",
            studentId = "18151210",
            year = "Faculté - Senior Research",
            ine = "9998887776B",
            email = "ada.lovelace@efrei.fr",
            phone = "+33 1 44 88 18 15",
            address = "Château d'Ockham\nSurrey, UK",
            dateOfBirth = "10/12/1815",
            moyenneGenerale = "19.8/20",
            enrollmentDate = "1 sept. 2018",
            expectedGraduation = "Faculty Tenured",
            validUntil = "09/2035",
            status = "Faculty/Staff",
            emergencyContactName = "Lord Byron",
            emergencyContactRelation = "Père",
            emergencyContactPhone = "+44 7700 900077"
        ),
        courses = listOf(
            Course("CYB901-COMP", "Advanced Quantum Cryptography", "Dr. Ada Lovelace", "09:00 - 12:00", "Restricted Lab N1", "Tue", 6),
            Course("CYB902-COMP", "History of Computational Engines", "Dr. Ada Lovelace", "14:00 - 17:00", "Amphi Euler", "Thu", 4)
        ),
        accessLevel = AccessLevel.TEACHER,
        password = "cyber_queen_1815",
        avatarEmoji = "👩‍🏫"
    )

    // 3. Ethan Hunt (Special Agent)
    val ethan = Profile(
        student = Student(
            firstName = "Ethan",
            lastName = "Hunt",
            program = "Master en Espionnage & Réseaux",
            studentId = "007-EFREI",
            year = "Infiltration - Spéciale",
            ine = "CLASSIFIED",
            email = "ethan.hunt@efrei.net",
            phone = "+33 6 00 07 007",
            address = "IMF Safehouse\nParis, France",
            dateOfBirth = "18/08/1964",
            moyenneGenerale = "CLASSIFIED/20",
            enrollmentDate = "CLASSIFIED",
            expectedGraduation = "NEVER",
            validUntil = "UNLIMITED",
            status = "Active Agent",
            emergencyContactName = "Luther Stickell",
            emergencyContactRelation = "Handler",
            emergencyContactPhone = "+33 6 99 99 99 99"
        ),
        courses = listOf(
            Course("IMF007-SYS", "Tactical Infiltration & Camouflage", "Director IMF", "23:00 - 02:00", "Site Classified", "Mon, Wed, Fri", 10),
            Course("IMF008-NET", "Advanced Wiretapping & Deepfakes", "Benji Dunn", "18:00 - 20:00", "Server Room B", "Tue", 8),
            Course("IMF009-SEC", "High-Altitude Base Jumping & Evacuation", "William Brandt", "06:00 - 09:00", "Helipad Site La Maison", "Sat", 12)
        ),
        accessLevel = AccessLevel.SPECIAL_AGENT,
        password = "mission_impossible",
        avatarEmoji = "🕵️‍♂️"
    )

    // 4. Thomas Anderson (Neo) (The One)
    val neo = Profile(
        student = Student(
            firstName = "Thomas",
            lastName = "Anderson (Neo)",
            program = "L3 - Déviation de la Réalité",
            studentId = "303-MATRIX",
            year = "The One - Awakening",
            ine = "0000000000X",
            email = "neo@efrei.net",
            phone = "+33 6 10 10 10 10",
            address = "Nebuchadnezzar\nCore Network",
            dateOfBirth = "11/03/1962",
            moyenneGenerale = "∞/20",
            enrollmentDate = "11 sept. 1999",
            expectedGraduation = "01/2030",
            validUntil = "01/2030",
            status = "The One",
            emergencyContactName = "Morpheus",
            emergencyContactRelation = "Mentor",
            emergencyContactPhone = "+33 6 44 44 44 44"
        ),
        courses = listOf(
            Course("MTX101-SYS", "Simulated Realities & Illusion", "Morpheus", "09:00 - 11:00", "Nebuchadnezzar Deck", "Mon", 5),
            Course("MTX102-KFU", "Kung-Fu via Direct Brain Download", "Operator Tank", "13:00 - 15:00", "Training Construct", "Wed", 5),
            Course("MTX103-SPN", "Spoon Bending & Physics Bypass", "The Oracle", "10:00 - 12:00", "Oracle's Kitchen", "Fri", 5)
        ),
        accessLevel = AccessLevel.THE_ONE,
        password = "follow_the_white_rabbit",
        avatarEmoji = "🕶️"
    )

    val profiles = listOf(gaspard, ada, ethan, neo)

    // Helper to filter facilities dynamically based on profile access levels
    fun getFacilitiesForAccess(accessLevel: AccessLevel): List<Facility> {
        val baseFacilities = listOf(
            Facility("Gym", "Site New Republic", "Bât. N", "Sport", 6, 30, 21, 30, FacilityIcon.GYM),
            Facility("Dance Room", "Site New Republic", "Bât. N", "Sport", 6, 30, 21, 30, FacilityIcon.DANCE),
            Facility("Crous", "Site La Maison", "Bât. A (Sous-sol)", "Restauration", 10, 30, 15, 30, FacilityIcon.DINING),
            Facility("Innovation Lab", "Site La Maison", "Bât. I", "Laboratoire", 7, 30, 20, 0, FacilityIcon.LAB),
            Facility("Student Hub", "Site La Maison", "Bât. F", "Espace étudiant", 7, 30, 20, 0, FacilityIcon.HUB),
            Facility("Lunch & Coworking", "Site La Factory", "Bât. H", "Restauration & Coworking", 7, 30, 20, 0, FacilityIcon.DINING),
            Facility("K-Fet", "Site La Maison", "Bât. E", "Cafétéria", 7, 30, 20, 0, FacilityIcon.CAFE)
        )

        return when (accessLevel) {
            AccessLevel.STUDENT -> baseFacilities
            
            AccessLevel.TEACHER -> baseFacilities + listOf(
                Facility("Faculty Lounge", "Site La Maison", "Bât. B (Étage 2)", "Faculty Only", 8, 0, 19, 0, FacilityIcon.HUB),
                Facility("Supercomputer Room", "Site La Maison", "Bât. I (Sous-sol)", "Restricted Lab", 0, 0, 23, 59, FacilityIcon.LAB)
            )
            
            AccessLevel.SPECIAL_AGENT -> baseFacilities + listOf(
                Facility("Secret IMF Server Room", "Site Classified", "Underground", "Security Hub", 0, 0, 23, 59, FacilityIcon.LAB),
                Facility("Heliport & Evac Zone", "Site La Maison", "Bât. F (Roof)", "Tactical", 0, 0, 23, 59, FacilityIcon.GYM)
            )
            
            AccessLevel.THE_ONE -> baseFacilities + listOf(
                Facility("Matrix Mainframe Terminal", "Site Nebuchadnezzar", "Core Deck", "Control Center", 0, 0, 23, 59, FacilityIcon.LAB),
                Facility("The Oracle's Kitchen", "Site Matrix", "Apartment 303", "Counseling", 8, 0, 22, 0, FacilityIcon.CAFE)
            )
        }
    }
}
