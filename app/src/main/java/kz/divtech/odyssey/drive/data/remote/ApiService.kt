package kz.divtech.odyssey.drive.data.remote

import kz.divtech.odyssey.drive.data.dto.auth.LoginResponseDto
import kz.divtech.odyssey.drive.data.dto.main.DailyInfoDto
import kz.divtech.odyssey.drive.data.dto.main.ShiftTimeDto
import kz.divtech.odyssey.drive.data.dto.main.SuccessResponse
import kz.divtech.odyssey.drive.data.dto.my_tasks.TaskListDto
import kz.divtech.odyssey.drive.data.dto.profile.ProfileDto
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskActionResponse
import kz.divtech.odyssey.drive.data.dto.task_detail.TaskDto
import kz.divtech.odyssey.drive.data.dto.task_detail.passengers.PassengerListDto
import kz.divtech.odyssey.drive.domain.model.auth.LoginRequest
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
    suspend fun getActiveTasks(@Query("page") page: Int,
                               @Query("from_date") fromDate: String,
                               @Query("to_date") toDate: String): TaskListDto

    @GET("driver-actions/tasks/archive")
    suspend fun getArchiveTasks(@Query("page") page: Int): TaskListDto

    @GET("driver-actions/tasks/passengers/{taskId}")
    suspend fun getPassengerListById(@Path("taskId") taskId: Int): PassengerListDto

    @POST("driver-actions/tasks/begin/{taskId}")
    suspend fun beginTaskById(@Path("taskId") taskId: Int): TaskActionResponse

    @POST("driver-actions/tasks/complete/{taskId}")
    suspend fun completeTaskById(@Path("taskId") taskId: Int): TaskActionResponse

    @POST("driver-actions/tasks/reject/{taskId}")
    suspend fun rejectTaskById(@Path("taskId") taskId: Int,
            @Body comment: Map<String, String>): TaskActionResponse
}