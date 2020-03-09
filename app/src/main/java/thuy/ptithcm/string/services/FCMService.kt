package com.thuypham.ptithcm.mytiki.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import thuy.ptithcm.string.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {

        Log.d("Tag", "Recive message=${p0.notification?.title}")
    }

    private fun sendNotification(notification: RemoteMessage.Notification) {
        val intent: Intent = Intent(this, MainActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT
        )
        val chaneId = "test_chanel_id"
        val defaultSoundUri = RingtoneManager
            .getActualDefaultRingtoneUri(
                this,
                RingtoneManager.TYPE_NOTIFICATION
            )
        val notificationBuilder = NotificationCompat.Builder(this, chaneId)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setContentTitle("EntryView")
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent);
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        val chane = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(
                chaneId,
                "MyTiki",

                NotificationManager.IMPORTANCE_DEFAULT
            )
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        notificationManager.notify(0, notificationBuilder.build())

    }

    fun runtimeEnableAutoInit() {
        // [START fcm_runtime_enable_auto_init]
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        // [END fcm_runtime_enable_auto_init]
    }

    override fun onNewToken(token: String) {
        Log.d( "MyFirebaseMsgService", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d("MyFirebaseMsgService", "sendRegistrationTokenToServer($token)")
    }

}