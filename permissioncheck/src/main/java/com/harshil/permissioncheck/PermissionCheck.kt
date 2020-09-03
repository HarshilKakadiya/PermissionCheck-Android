package com.harshil.permissioncheck

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import java.lang.ref.WeakReference

object PermissionCheck {

    /**
     * [allPermission] is use to store all permission which is ask from user to
     * input/output operation
     * */
    internal lateinit var allPermission: List<String>

    /**
     * [permissionNeed] is use to store detect which permission are not given by user
     * */
    internal var permissionNeed: ArrayList<String> = ArrayList()

    /**
     * [isSkipDenyAndForceDeny] is use to to detect that given permission is compulsory or not
     * */
    internal var isSkipDenyAndForceDeny: Boolean = false

    /**
     * [acceptedCallback] is use to revert back to user when user accept all the given permission
     * */
    internal var acceptedCallback: WeakReference<PermissionStatus>? = null

    /**
     * [deniedCallback] is use to revert back to user when user deny for any one permission
     * among given permissions
     * */
    internal var deniedCallback: WeakReference<PermissionStatus>? = null

    /**
     * [foreverDeniedCallback] is use to revert back to user when user force deny (Don't ask again)
     * for any one permission among given permissions
     * */
    internal var foreverDeniedCallback: WeakReference<PermissionStatus>? = null

    /**
     * [REQ_PERMISSION_CODE] is use to catch the permission result
     * */
    internal const val REQ_PERMISSION_CODE = 1

    /**
     * [with] is use to get context of current Activity or Fragment
     * */
    @JvmStatic
    fun with(activity: Activity?): PermissionExecution {
        return PermissionExecution(activity)
    }

    /**
     * [PermissionExecution] is Builder Design Pattern class
     * */
    class PermissionExecution(private val mContext: Activity?) {

        /**
         * To set all the permission which is required
         * */
        fun setPermissions(vararg _allPermission: String) = this@PermissionExecution.apply {
            allPermission = _allPermission.toList()
        }

        /**
         * To set whether permission is required or not
         * Default it is required (TRUE)
         * */
        fun skipDenyAndForceDeny() = this@PermissionExecution.apply {
            isSkipDenyAndForceDeny = true
        }

        /**
         * Accept call back - when user accept all the permission
         * This function is used for Kotlin language
         * */
        fun onAccepted(callback: (list: MutableList<String>?) -> Unit) =
            this@PermissionExecution.apply {
                acceptedCallback = WeakReference(object : PermissionStatus {
                    override fun onResult(list: MutableList<String>?) {
                        callback(list)
                    }
                })
            }

        /**
         * Accept call back - when user accept all the permission
         * This function is used for Java language
         * */
        fun onAccepted(callback: PermissionStatus) = this@PermissionExecution.apply {
            acceptedCallback = WeakReference(callback)
        }

        /**
         * Accept call back - when user deny for any one permission
         * This function is used for Kotlin language
         * */
        fun onDenied(callback: (list: MutableList<String>?) -> Unit) =
            this@PermissionExecution.apply {
                deniedCallback = WeakReference(object : PermissionStatus {
                    override fun onResult(list: MutableList<String>?) {
                        callback(list)
                    }
                })
            }

        /**
         * Accept call back - when user deny for any one permission
         * This function is used for Java language
         * */
        fun onDenied(callback: PermissionStatus) = this@PermissionExecution.apply {
            deniedCallback = WeakReference(callback)
        }

        /**
         * Accept call back - when user force deny (Don't ask again) for any one permission
         * This function is used for Kotlin language
         * */
        fun onForeverDenied(callback: (list: MutableList<String>?) -> Unit) =
            this@PermissionExecution.apply {
                foreverDeniedCallback = WeakReference(object : PermissionStatus {
                    override fun onResult(list: MutableList<String>?) {
                        callback(list)
                    }
                })
            }

        /**
         * Accept call back - when user force deny (Don't ask again) for any one permission
         * This function is used for Java language
         * */
        fun onForeverDenied(callback: PermissionStatus) =
            this@PermissionExecution.apply {
                foreverDeniedCallback = WeakReference(callback)
            }

        /**
         * when user call for [ask] function the execution of asking permissions will be executed
         * */
        fun ask() {
            mContext?.apply {

                /**
                 * If [Activity] which gives context is finishing then nothing will happen and return
                 * */
                if (isFinishing) {
                    return
                }

                /**
                 * If all given permission is already granted then no need to go for further
                 * process and revert back to user here and return
                 * */
                if (alreadyGranted(this as Context)) {
                    acceptedCallback?.get()?.onResult(allPermission as ArrayList<String>)
                    return
                }

                /**
                 * If any one permission is not given then start activity to ask for permission
                 * */
                mContext.startActivity(Intent(this, PermissionCheckActivity::class.java))
                mContext.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }

        /**
         * Checking for permission is given or not
         * */
        private fun alreadyGranted(context: Context): Boolean {
            permissionNeed.clear()
            allPermission.filterTo(permissionNeed) {
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }
            return permissionNeed.isEmpty()
        }
    }
}