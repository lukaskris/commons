package com.evodream.app.accountcommons.register

/**
 * @author Lukaskris
 * @since 2018/01/07
 */
interface RegisterNavigator {
    fun onNameEmpty()
    fun onEmailEmpty()
    fun onEmailNotMatchFormat()
    fun onPasswordEmpty()
    fun onPhoneEmpty()
}