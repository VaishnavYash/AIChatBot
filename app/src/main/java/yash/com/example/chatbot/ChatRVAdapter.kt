package yash.com.example.chatbot


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*


class ChatRVAdapter(messageModalArrayList: ArrayList<ChatModal>, context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val messageModalArrayList: ArrayList<ChatModal>
    private val context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        var messageTV: TextView
        var dateTV: TextView
        when (viewType) {
            0 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.user_msg, parent, false)
                return UserViewHolder(view)
            }
            1 -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.ai_res, parent, false)
                return BotViewHolder(view)
            }
        }
        return null!!
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val modal: ChatModal = messageModalArrayList[position]
        when (modal.getSender()) {
            "user" ->
                (holder as UserViewHolder).userTV.text = modal.getMessage()
            "bot" ->
                (holder as BotViewHolder).botTV.text=modal.getMessage()
        }
    }

    override fun getItemCount(): Int {
        return messageModalArrayList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (messageModalArrayList[position].getSender()) {
            "user" -> 0
            "bot" -> 1
            else -> -1
        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var userTV: TextView

        init {
            userTV = itemView.findViewById(R.id.user_msg)
        }
    }

    @SuppressLint("SimpleDateFormat")
    class BotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var botTV: TextView
        init {
            botTV = itemView.findViewById(R.id.ai_msg)
        }
    }

    init {
        this.messageModalArrayList = messageModalArrayList
        this.context = context
    }
}