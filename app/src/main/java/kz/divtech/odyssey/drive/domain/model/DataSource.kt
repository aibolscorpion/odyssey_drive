package kz.divtech.odyssey.drive.domain.model

import kz.divtech.odyssey.drive.domain.model.main.Task
import kz.divtech.odyssey.drive.presentation.ui.screens.task_detail.Status

object DataSource {

    val passengerList = listOf(
        "Баталгазин Руслан", "Онгаров Айбол", "Кулжабай Магжан",
        "Василий Пупкин", "Василий Иваныч","Баталгазин Руслан", "Онгаров Айбол",
        "Кулжабай Магжан", "Василий Пупкин", "Василий Иваныч")

    val task: Task = Task(
        time = "08:00 – 20:00",
        departureArrivalPlace = "Вокзал «Актогай» — ГОК",
        id = 163,
        passengerCount = 14,
        status = Status.IN_PROGRESS,
        date = "23 сент, вт")

}