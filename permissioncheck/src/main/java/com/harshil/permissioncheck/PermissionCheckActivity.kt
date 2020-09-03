package com.harshil.permissioncheck

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.harshil.permissioncheck.PermissionCheck.REQ_PERMISSION_CODE
import com.harshil.permissioncheck.PermissionCheck.acceptedCallback
import com.harshil.permissioncheck.PermissionCheck.deniedCallback
import com.harshil.permissioncheck.PermissionCheck.foreverDeniedCallback
import com.harshil.permissioncheck.PermissionCheck.isSkipDenyAndForceDeny
import com.harshil.permissioncheck.PermissionCheck.permissionNeed

class PermissionCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_check)

        /**
         * As soon as activity will be created will ask for permission which is not given
         * */
        ActivityCompat.requestPermissions(
            this@PermissionCheckActivity,
            permissionNeed.toTypedArray(),
            REQ_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQ_PERMISSION_CODE -> {

                /**
                 * First list = is a list of all permission which is granted by user
                 * Second list = is a list of all permission which is denied by user
                 * Third list = is a list of all permission which is force denied (Don't ask again) by user
                 * */
                val acceptedPermissions = mutableListOf<String>()
                val askAgainPermissions = mutableListOf<String>()
                val refusedPermissions = mutableListOf<String>()

                for (i in permissions.indices) {
                    val permissionName = permissions[i]
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        acceptedPermissions.add(permissionName)
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                            shouldShowRequestPermissionRationale(permissionName)
                        ) {
                            askAgainPermissions.add(permissionName)
                        } else {
                            refusedPermissions.add(permissionName)
                        }
                    }
                }

                /**
                 * First check for if any force denied permission is contain or not
                 * Second check for if any permission if simple denied or not
                 * If First and Second condition is false that means user has given all the given permission
                 * */
                when {
                    refusedPermissions.isNotEmpty() && !isSkipDenyAndForceDeny -> foreverDeniedCallback?.get()
                        ?.onResult(refusedPermissions)
                    askAgainPermissions.isNotEmpty() && !isSkipDenyAndForceDeny -> deniedCallback?.get()
                        ?.onResult(askAgainPermissions)
                    acceptedPermissions.isNotEmpty() || isSkipDenyAndForceDeny -> acceptedCallback?.get()
                        ?.onResult(acceptedPermissions)
                }
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

    override fun onDestroy() {
        /**
         * Reset all the variable to it's position
         * */
        isSkipDenyAndForceDeny = false
        acceptedCallback?.clear()
        deniedCallback?.clear()
        acceptedCallback?.clear()
        super.onDestroy()
    }

}
