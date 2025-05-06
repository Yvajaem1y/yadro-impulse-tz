package com.androidlesson.yadro_tz_2025.presentation.activities

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.Manifest
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import com.androidlesson.yadro_tz_2025.R
import com.androidlesson.yadro_tz_2025.app.App
import com.androidlesson.yadro_tz_2025.presentation.adapters.ContactAdapter
import com.androidlesson.yadro_tz_2025.presentation.viewModels.MainActivityViewModel
import com.androidlesson.yadro_tz_2025.presentation.viewModels.MainActivityViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS = 101

    private lateinit var vm : MainActivityViewModel
    @Inject
    lateinit var vmFactory: MainActivityViewModelFactory

    private lateinit var rv_contacts_holder: RecyclerView

    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBarColor()

        checkContactsPermission()
    }

    private fun setBarColor() {
        val statusBarColor = Color.parseColor("#FFFFFF")
        val navigationBarColor = Color.parseColor("#F5F5F5")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window

            window.statusBarColor = statusBarColor

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                window.navigationBarColor = navigationBarColor
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }

    private fun checkContactsPermission() {
        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_CONTACTS)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.CALL_PHONE)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS
            )
        } else {
            setupViewModelAndUI()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSIONS) {
            var allGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    break
                }
            }

            if (allGranted) {
                setupViewModelAndUI()
            } else {
                Toast.makeText(this, "Необходимо разрешение для чтения контактов и звонков", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupViewModelAndUI() {
        (application as App).appComponent?.injectMainActivity(this)
        vm = ViewModelProvider(this, vmFactory).get(MainActivityViewModel::class.java)

        initialization()
        observer()
    }

    private fun initialization() {
        rv_contacts_holder=findViewById(R.id.rv_contacts_holder)
        rv_contacts_holder.layoutManager= LinearLayoutManager(this)

        contactAdapter=ContactAdapter()
        rv_contacts_holder.adapter=contactAdapter
    }

    private fun observer(){
        vm.getContactsLiveData().observe(this){
            contactAdapter.updateContacts(it)
        }
    }
}