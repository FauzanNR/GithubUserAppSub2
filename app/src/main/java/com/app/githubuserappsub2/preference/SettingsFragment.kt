package com.app.githubuserappsub2.preference

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.preference.PreferenceFragmentCompat
import com.app.githubuserappsub2.R

class SettingsFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var alarmNotificationBroadReceiver: AlarmNotificationBroadReceiver
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Settings", context.toString())
        alarmNotificationBroadReceiver = AlarmNotificationBroadReceiver(context as Context)
        Log.d("shared", "is alarm setted ${alarmNotificationBroadReceiver.isAlarmSet()}")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "notif") {
            if (sharedPreferences?.getBoolean(key, false) == true) {
                alarmNotificationBroadReceiver.setAlarm()
                Log.d("shared", "is alarm setted ${alarmNotificationBroadReceiver.isAlarmSet()}")
            } else {
                alarmNotificationBroadReceiver.cancelAlarm()
                Log.d("shared", "is alarm setted ${alarmNotificationBroadReceiver.isAlarmSet()}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {

        menu.findItem(R.id.fav_btn_detail).isVisible = false
        menu.findItem(R.id.setting_btn).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().invalidateOptionsMenu()
        super.onCreateOptionsMenu(menu, inflater)
    }

}