package com.example.di.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.di.BaseApp
import com.example.di.DaggerAppComponent
import com.example.utils.eLog
import dagger.android.AndroidInjection
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment

object AppInjector {
    fun init(app: BaseApp) {
        DaggerAppComponent.builder()
            .application(app)
            .context(app.applicationContext)
            .build()
            .inject(app)
        app
            .registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity?) {}
                override fun onActivityResumed(activity: Activity?) {}
                override fun onActivityStarted(activity: Activity?) {}
                override fun onActivityDestroyed(activity: Activity?) {}
                override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
                override fun onActivityStopped(activity: Activity?) {}
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    handleActivity(activity)
                }
            })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is HasAndroidInjector) {
            AndroidInjection.inject(activity)
        }
        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(
                    object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(
                            fm: FragmentManager,
                            f: Fragment,
                            savedInstanceState: Bundle?
                        ) {
                            if (f is DaggerFragment) {
                                try {
                                    AndroidSupportInjection.inject(f)
                                } catch (exception: IllegalArgumentException) {
                                    exception.printStackTrace()
                                    "Don\'t forget to add fragments into FragmentBuildersModule".eLog()
                                }
                            }
                        }
                    }, true
                )
        }
    }
}
