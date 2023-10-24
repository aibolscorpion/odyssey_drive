package kz.divtech.odyssey.drive.domain.model.main


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.Status

@Parcelize
data class Task(val time: String,
                val departureArrivalPlace: String,
                val id: Int,
                val passengerCount: Int,
                val status: Status,
                val date: String): Parcelable

val emptyTask = Task("", "", 0, 0, Status.ASSIGNED, "")
