package kz.divtech.odyssey.drive.common

import kz.divtech.odyssey.drive.data.dto.task_detail.TaskDto
import java.lang.StringBuilder
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeUtils {
    private const val dayOfMonthWeekFull = "d MMMM, EEEE"
    private const val dateTimePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val datePattern = "yyyy-MM-dd"
    private const val timePattern = "HH:mm"
    private const val dayOfMonthWeekShort = "d MMM EE"

    fun String.formatToTime(): String{
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
        val timeFormatter = DateTimeFormatter.ofPattern(timePattern)

        val parsedDateTime = dateTimeFormatter.parse(this)
        return timeFormatter.format(parsedDateTime)
    }

    fun String.parseToLocalDate(): LocalDate{
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
        return LocalDate.parse(this, dateTimeFormatter)
    }

    fun String.isDateToday(): Boolean{
        return this.parseToLocalDate() == LocalDate.now()
    }

    fun String.formatToDateWithText(): String{
        val strBuilder = StringBuilder(this.formatToDate())
        if(this.isDateToday()) {
            strBuilder.append(" (Сегодня)")
        }
        return strBuilder.toString()
    }

    fun String.formatToDate(): String{
        val dateFormatter = DateTimeFormatter.ofPattern(dayOfMonthWeekShort, Locale("ru"))

        return dateFormatter.format(this.parseToLocalDateTime())
    }

    fun getCurrentDate(): String{
        val dayOfMonthAndWeekPattern = DateTimeFormatter.ofPattern(dayOfMonthWeekFull, Locale("ru"))
        return dayOfMonthAndWeekPattern.format(LocalDate.now())
    }

    fun LocalDateTime.isBtwTaskTime(task: TaskDto): Boolean{
        return this in task.actionStartTime.parseToLocalDateTime()..task.actionEndTime.parseToLocalDateTime()
    }


    fun String.parseToLocalDateTime(): LocalDateTime{
        val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
        return LocalDateTime.parse(this, dateTimeFormatter)
    }

    fun LocalDate.formatToStringDate(): String{
        val dateFormatter = DateTimeFormatter.ofPattern(datePattern)
        return dateFormatter.format(this)
    }

    fun LocalDate.formatToDatePickerString(): String{
        val dayOfMonthWeekShortFormatter = DateTimeFormatter.ofPattern(dayOfMonthWeekShort, Locale("ru"))
        return dayOfMonthWeekShortFormatter.format(this)
    }

    fun Long.toLocalDate(): LocalDate =
        Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

}