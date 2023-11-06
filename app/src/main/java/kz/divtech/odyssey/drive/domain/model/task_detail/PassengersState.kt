package kz.divtech.odyssey.drive.domain.model.task_detail

 class PassengersState(val isLoading: Boolean = false,
                       val error: String = "",
                       val passengerList: List<Passenger> = emptyList())