package caramelo.com.br.gasstationwarning.data.fcm

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "FCMMessagingService"

class FCMMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "From: " + remoteMessage?.from)
        Log.d(TAG, "Message data payload: " + remoteMessage?.data);
        Log.d(TAG, "Message Notification Body: " + remoteMessage?.notification?.body)
    }

}