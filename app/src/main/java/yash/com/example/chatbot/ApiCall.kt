package yash.com.example.chatbot

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Url

interface ApiCall {

    @GET()
    fun getMessage(@Url url:String) : Call <MsgModal>

}