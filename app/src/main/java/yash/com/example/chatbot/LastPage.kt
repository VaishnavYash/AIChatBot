package yash.com.example.chatbot

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LastPage : AppCompatActivity() {

    private var chatsRV: RecyclerView? = null
    private var sendMsgIB: ImageButton? = null
    private var userMsgEdt: EditText? = null
    private val USER_KEY = "user"
    private val BOT_KEY = "bot"
    private  var chatsModelArrayList : ArrayList<ChatModal>? = null
    private  var chatsAdapter : ChatRVAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_page)


        val actionBar = supportActionBar
        actionBar!!.title = "Mike"
        actionBar.subtitle = "Depression ChatBot"
        actionBar.setIcon(R.mipmap.ic_launcher_round)
        actionBar.setDisplayUseLogoEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        chatsRV = findViewById(R.id.idRVChats)
        sendMsgIB = findViewById(R.id.etrBtn)
        userMsgEdt = findViewById(R.id.edtMsg)

        chatsModelArrayList = ArrayList()
        chatsAdapter = ChatRVAdapter(chatsModelArrayList!!, this)

        val linearLayoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        chatsRV?.layoutManager = linearLayoutManager
        chatsRV?.adapter = chatsAdapter

        sendMsgIB?.setOnClickListener{

            if(userMsgEdt?.text?.isEmpty() == true){
                Toast.makeText(this, "Please Enter your Message", Toast.LENGTH_SHORT).show()
            }else {
                getResponse(userMsgEdt?.text.toString())
                userMsgEdt?.setText("")
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onStart() {
        super.onStart()
        chatsModelArrayList?.add(ChatModal("I am Mike, developed by Yash. You can talk to me freely.", BOT_KEY))
        chatsAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getResponse(message: String){
        chatsModelArrayList?.add(ChatModal(message, USER_KEY))
        chatsAdapter?.notifyDataSetChanged()

        val url =
            "http://api.brainshop.ai/get?bid=170147&key=mTSTvzcdMjxbeRi2&uid=[uid]&msg=$message"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiCall: ApiCall = retrofit.create(ApiCall::class.java)
        val call : Call<MsgModal> = apiCall.getMessage(url)

        call.enqueue(object: Callback<MsgModal> {
            override fun onResponse(call: Call<MsgModal>, response : Response<MsgModal>) {
                if(response.isSuccessful){
                    val modal: MsgModal? = response.body()
                    chatsModelArrayList?.add(ChatModal(modal?.getCnt()!!, BOT_KEY))
                    chatsAdapter?.notifyDataSetChanged()
                }
                chatsRV?.post {
                    chatsRV?.smoothScrollToPosition(chatsAdapter?.itemCount!! - 1)
                }
            }
            override fun onFailure(call: Call<MsgModal>, t: Throwable) {
                chatsModelArrayList?.add(ChatModal("Please revert your Question", BOT_KEY))
                chatsAdapter?.notifyDataSetChanged()
                chatsRV?.post{
                    chatsRV?.smoothScrollToPosition(chatsAdapter?.itemCount!! - 1)
                }
            }
        })
    }
}

