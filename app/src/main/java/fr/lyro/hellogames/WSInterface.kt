package fr.lyro.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WSInterface {

    @GET("game/list")
    fun listGames(): Call<List<GameLinkObject>>

    @GET("game/details")
    fun getGameDetails(@Query("game_id") userId: Int): Call<GameObject>

}