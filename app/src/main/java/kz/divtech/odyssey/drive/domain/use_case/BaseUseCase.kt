package kz.divtech.odyssey.drive.domain.use_case


interface BaseUseCase<In, Out>{
    suspend fun execute(input: In): Out
}

interface BaseUseCase2Input<In1, In2, Out>{
    suspend fun execute(input: In1, input2: In2): Out
}