package kz.divtech.odyssey.drive.domain.model.main


import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.Status

data class Task(val time: String,
                val departureArrivalPlace: String,
                val id: Int,
                val passengerCount: Int,
                val status: Status,
                val actualStartTime: String,
                val actualEndTime: String,
                val date: String,
                val seatCount: Int,
                val cancelReason: String,
                val cancelTime: String,
                val isCompletedInTime: Boolean)

val emptyTask = Task("", "", 0, 0, Status.ASSIGNED,
    "",  "", "", 0, "", "", false)
