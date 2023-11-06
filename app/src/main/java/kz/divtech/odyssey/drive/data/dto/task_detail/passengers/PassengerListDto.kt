package kz.divtech.odyssey.drive.data.dto.task_detail.passengers

import kz.divtech.odyssey.drive.domain.model.task_detail.Passenger


class PassengerListDto : ArrayList<PassengerListDtoItem>()

fun PassengerListDto.toPassengerList() : List<Passenger> {
    return this.map {
        Passenger(seatNumber = it.seatNumber, fullName = it.passengerDto.fullName)
    }
}
