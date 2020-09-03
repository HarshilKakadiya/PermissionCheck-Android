package com.harshil.permissioncheck

interface PermissionStatus {
    fun onResult(list: MutableList<String>? = null)
}