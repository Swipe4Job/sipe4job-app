package cat.dam.grup2.swipe4job_app.features.recruiter.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Date

data class RecruiterConnection(
    val candidateName: String,
    val candidateLastName: String,
    val candidatePhone: String,
    val candidateEmail: String,
    val jobOfferTitle: String,
    val connectionDate: Date
)