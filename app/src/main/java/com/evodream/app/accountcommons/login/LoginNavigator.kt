package com.evodream.app.accountcommons.login

/**
 * @author lukaskris
 * @since 2017/12/28
 */
interface LoginNavigator {
	fun onLoginSuccess()
	fun onLoginError(throwable: Throwable)
	fun onEmailEmpty()
	fun onEmailNotMatchFormat()
	fun onPasswordEmpty()
}