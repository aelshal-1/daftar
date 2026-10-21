package com.aelshal.daftar

import android.os.Build

actual fun platformName(): String = "Android ${Build.VERSION.RELEASE}"
