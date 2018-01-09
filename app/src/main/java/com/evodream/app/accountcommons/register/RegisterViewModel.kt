package com.evodream.app.accountcommons.register

import android.content.Context
import android.databinding.Bindable
import android.databinding.ObservableField
import com.evodream.app.accountcommons.base.BaseViewModel
import java.util.regex.Pattern

/**
 * @author Lukaskris
 * @since 2018/01/07
 */
class RegisterViewModel(context: Context, navigator:RegisterNavigator) : BaseViewModel<Context, RegisterNavigator>(context, navigator) {

    private val name = ObservableField<String>()
    private val email = ObservableField<String>()
    private val phone = ObservableField<String>()
    private val password = ObservableField<String>()

    init {
        name.set("")
        phone.set("")
        email.set("")
        password.set("")
    }

    @Bindable
    fun getName(): String = name.get()

    @Bindable
    fun getPassword(): String = password.get()

    @Bindable
    fun getEmail(): String = email.get()

    @Bindable
    fun getPhone(): String = phone.get()

    fun onRegister(){
        if(isRegisterValid(name.get(), email.get(), phone.get(), password.get())){
            // Todo set register here
        }
    }

    private fun isRegisterValid(name:String, email:String, phone:String, password: String):Boolean{
        var isValid = true
        if(name.isEmpty()) {
            isValid = false
            mNavigator.onNameEmpty()
        }else if(email.isEmpty()){
            isValid = false
            mNavigator.onEmailEmpty()
        } else if(!isEmailValid(email)) {
            isValid = false
            mNavigator.onEmailNotMatchFormat()
        } else if(phone.isEmpty()){
            isValid = false
            mNavigator.onPhoneEmpty()
        } else if(password.isEmpty()){
            isValid = false
            mNavigator.onPasswordEmpty()
        }
        return isValid
    }

    private fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
}