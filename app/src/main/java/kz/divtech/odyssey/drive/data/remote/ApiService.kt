package kz.divtech.odyssey.drive.data.remote

import kz.divtech.odyssey.drive.data.dto.login.LoginResponseDto
import kz.divtech.odyssey.drive.data.dto.main.DailyInfoDto
import kz.divtech.odyssey.drive.data.dto.main.ShiftTimeDto
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.data.dto.my_tasks.TaskListDto
import kz.divtech.odyssey.drive.data.dto.profile.ProfileDto
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskDto
import kz.divtech.odyssey.drive.domain.model.login.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("auth-driver/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponseDto

    @GET("driver-account")
    suspend fun getProfile(): ProfileDto

    @GET("driver-actions/daily-info")
    suspend fun getDailyInfo(): DailyInfoDto

    @POST("driver-actions/shift-routine/activate")
    suspend fun activateShift(): SuccessResponse

    @POST("driver-actions/shift-routine/break")
    suspend fun pauseShift(): SuccessResponse

    @POST("driver-actions/shift-routine/resume")
    suspend fun resumeShift(): SuccessResponse

    @POST("driver-actions/shift-routine/deactivate")
    suspend fun deactivateShift(): SuccessResponse

    @GET("driver-actions/shift-routine/timer")
    suspend fun getShiftTime(): ShiftTimeDto

    @GET("driver-actions/tasks/one/{id}")
    suspend fun getTaskById(@Path("id") id: Int): TaskDto

    @GET("driver-actions/tasks/active")
    suspend fun getActiveTasks(@Query("page") page: Int): TaskListDto

    @GET("driver-actions/tasks/archive")
    suspend fun getArchiveTasks(@Query("page") page: Int): TaskListDto
}