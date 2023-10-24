package kz.divtech.odyssey.drive.domain.model.main

import java.time.LocalDateTime


data class ShiftTime(
    val shiftStatus: ShiftStatus,
    val shiftRoutineStartTime: LocalDateTime?
)