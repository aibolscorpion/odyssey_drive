package kz.divtech.odyssey.drive.data.dto.main


import com.google.gson.annotations.SerializedName
import kz.divtech.odyssey.drive.common.DateTimeUtils.parseToLocalDateTime
import kz.divtech.odyssey.drive.domain.model.main.ShiftTime
import kz.divtech.odyssey.drive.domain.model.main.toShiftStatus

data class ShiftTimeDto(
    @SerializedName("daily_status")
    val dailyStatus: String,
    @SerializedName("shift_routine_start_time")
    val shiftRoutineStartTime: String?
)

fun ShiftTimeDto.toShiftTime() =
    ShiftTime(shiftRoutineStartTime = shiftRoutineStartTime?.parseToLocalDateTime(), shiftStatus = dailyStatus.toShiftStatus())