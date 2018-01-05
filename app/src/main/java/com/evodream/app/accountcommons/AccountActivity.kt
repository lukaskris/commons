package com.evodream.app.accountcommons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.evodream.app.accountcommons.login.LoginFragment
import com.evodream.app.accountcommons.util.AndroidUtil

class AccountActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		supportActionBar?.setDisplayShowHomeEnabled(true)
		AndroidUtil.switchTo(R.id.fragment_container, LoginFragment.newInstance(), supportFragmentManager)
	}
}
