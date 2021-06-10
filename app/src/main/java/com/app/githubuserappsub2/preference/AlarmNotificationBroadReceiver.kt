package com.app.githubuserappsub2.preference

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import java.util.*

open class AlarmNotificationBroadReceiver() : BroadcastReceiver() {

    private lateinit var notification: NotificationAlaram
    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context = context
    }

    override fun onReceive(context: Context, intent: Intent) {
        notification = NotificationAlaram(context)
        notification.sendNotification()
    }

    companion object {
        const val ID_REPEATING = 101
    }

    fun cancelAlarm() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationBroadReceiver::class.java)
        val pendingAlarm = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)

        alarmManager.cancel(pendingAlarm)
        Toast.makeText(context, "Notification OFF", Toast.LENGTH_SHORT).show()
    }

    fun setAlarm() {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmNotificationBroadReceiver::class.java)


        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 9)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)


        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
        Toast.makeText(context, "Notification ON", Toast.LENGTH_SHORT).show()
    }

    fun isAlarmSet(): Boolean {
        val intent = Intent(context, AlarmNotificationBroadReceiver::class.java)
        return PendingIntent.getBroadcast(
            context,
            ID_REPEATING,
            intent,
            PendingIntent.FLAG_NO_CREATE
        ) != null
    }
}