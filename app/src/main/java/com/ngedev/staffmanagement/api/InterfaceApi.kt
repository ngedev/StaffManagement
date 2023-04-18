package com.ngedev.staffmanagement.api

import com.ngedev.staffmanagement.api.model.ResponseLogin
import com.ngedev.staffmanagement.api.model.ResponseRegister
import com.ngedev.staffmanagement.api.model.ResponseUsers
import com.ngedev.staffmanagement.api.model.Staff
import retrofit2.http.*

interface InterfaceApi {

    @FormUrlEncoded
    @POST("/api/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String
    ): Result<ResponseRegister>

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Result<ResponseLogin>

    @GET("/api/users")
    suspend fun users(): Result<ResponseUsers>

    @FormUrlEncoded
    @POST("/api/users")
    suspend fun addStaff(
        @Field("name") name: String,
        @Field("job") job: String
    ): Result<Staff>

    @FormUrlEncoded
    @PUT
    suspend fun updateStaff(
        @Url url: String,
        @Field("name") name: String,
        @Field("job") job: String
    ): Result<Staff>

    @DELETE
    suspend fun deleteStaff(
        @Url url: String
    ): Result<Staff>

}