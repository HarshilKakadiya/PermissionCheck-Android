package com.harshil.permissioncheckexample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.harshil.permissioncheck.PermissionCheck

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onAsk(view: View) {
        PermissionCheck.with(this@MainActivity)
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO
            )
            .skipDenyAndForceDeny()
            .onAccepted {
                Toast.makeText(this@MainActivity, "Set all the permission", Toast.LENGTH_SHORT)
                    .show()
                Log.d(javaClass.simpleName, "onAccepted: $it ")
            }
            .onDenied {
                Toast.makeText(
                    this@MainActivity,
                    "You just denied for the permission",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(javaClass.simpleName, "onDenied: $it")
            }
            .onForeverDenied {
                Toast.makeText(
                    this@MainActivity,
                    "You just force denied for the permission",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d(javaClass.simpleName, "onForeverDenied: $it")
            }
            .ask()
    }
}