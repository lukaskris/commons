package com.evodream.app.accountcommons.login

import android.content.Context
import android.databinding.Bindable
import android.databinding.ObservableField
import com.evodream.app.accountcommons.base.BaseViewModel
import java.util.regex.Pattern

/**
 * @author lukaskris
 * @since 2017/12/28
 */
class LoginViewModel(context:Context, navigator:LoginNavigator) : BaseViewModel<Context, LoginNavigator> (context, navigator){

	private val email = ObservableField<String>()
	private val password = ObservableField<String>()

	init {
		email.set("")
		password.set("")
	}

	@Bindable
	fun getEmail() = email.get()

	@Bindable
	fun setEmail(email:String){
		this.email.set(email)
	}

	@Bindable
	fun getPassword() = password.get()

	@Bindable
	fun setPassword(password:String){
		this.email.set(password)
	}

	private fun isSignInValid(email:String, password: String):Boolean{
		var isValid = true
		if(email.isEmpty()){
			isValid = false
			mNavigator.onEmailEmpty()
		} else if(!isEmailValid(email)){
			isValid = false
			mNavigator.onEmailNotMatchFormat()
		} else if(password.isEmpty()){
			isValid = false
			mNavigator.onPasswordEmpty()
		}
		return isValid
	}

	fun onLogin(){
		if(isSignInValid(getEmail(), getPassword())){

		}
	}

	fun isEmailValid(email: String): Boolean {
		val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
		val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
		val matcher = pattern.matcher(email)
		return matcher.matches()
	}
}