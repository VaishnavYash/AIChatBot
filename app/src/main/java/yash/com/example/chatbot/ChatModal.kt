package yash.com.example.chatbot

class ChatModal(private var message: String,private var sender: String) {

    fun getMessage(): String {
        return message
    }
    fun getSender(): String {
        return sender
    }

}
