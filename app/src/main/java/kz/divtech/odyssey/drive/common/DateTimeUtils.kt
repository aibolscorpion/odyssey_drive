package kz.divtech.odyssey.drive.common

import kz.divtech.odyssey.drive.data.dto.task_detail.TaskDto
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object DateTimeUtils {
    private val dayOfMonthAndWeek = SimpleDateFormat("dd MMMM, EEEE", Locale("ru"))
    private const val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val timePattern = "HH:mm"
    private const val dayPattern = "d MMMM"

    fun String.formatToTime(): String{
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
        val timeFormatter = DateTimeFormatter.ofPattern(timePattern)

        val parsedDateTime = dateTimeFormatter.parse(this)
        return timeFormatter.format(parsedDateTime)
    }

    fun String.formatToDate(): String{
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
        val dateFormatter = DateTimeFormatter.ofPattern(dayPattern, Locale("ru"))

        val parsedDate = dateTimeFormatter.parse(this)
        return dateFormatter.format(parsedDate)
    }

    fun getTodayDate(): String{
        val calendar = Calendar.getInstance()
        return dayOfMonthAndWeek.format(calendar.timeInMillis)
    }

    fun TaskDto.isTaskOnTheWaiting(): Boolean{
        val now = LocalDateTime.now()

        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
        val parsedStartTime = LocalDateTime.parse(this.actionStartTime, dateTimeFormatter)
        val parsedEndTime = LocalDateTime.parse(this.actionEndTime, dateTimeFormatter)

        return now in parsedStartTime..parsedEndTime
    }

    fun String?.toLocalDateTime(): LocalDateTime?{
        this?.let {
            val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
            return LocalDateTime.parse(this, dateTimeFormatter)
        }
        return null
    }
}