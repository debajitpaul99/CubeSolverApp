package com.debajit.cubesolver.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CubeApi {

    @POST("solve")
    suspend fun solve(
        @Body request: SolveRequest
    ): Response<SolveResponse>
}