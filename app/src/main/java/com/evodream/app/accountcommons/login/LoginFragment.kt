package com.evodream.app.accountcommons.login

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evodream.app.accountcommons.R
import com.evodream.app.accountcommons.base.BaseFragment
import com.evodream.app.accountcommons.databinding.LoginBinding
import com.evodream.app.accountcommons.util.AndroidUtil
import com.evodream.app.accountcommons.util.ExceptionUtil
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import java.util.*


/**
 * @author lukaskris
 * @since 2017/12/28
 */
class LoginFragment : BaseFragment(), LoginHandler, LoginNavigator{

	private lateinit var mViewModel: LoginViewModel
	private lateinit var mBinding: LoginBinding
	private lateinit var mCallBackManager: CallbackManager
	private lateinit var mAuth: FirebaseAuth

	companion object {
		val TAG = "LoginFragment"
		@JvmStatic
		fun newInstance() = LoginFragment()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
		(activity as AppCompatActivity).supportActionBar?.title = getString(R.string.lit_login)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.login, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mViewModel = LoginViewModel(context!!, this)
		mBinding = DataBindingUtil.bind(view)
		mBinding.handler = this
		mBinding.viewModel = mViewModel
		mAuth = FirebaseAuth.getInstance()
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		mCallBackManager.onActivityResult(requestCode, resultCode, data)
	}

	fun handleFacebookToken(token:AccessToken){
		activity?.let {
			val credential = FacebookAuthProvider.getCredential(token.token)
			mAuth.signInWithCredential(credential).addOnCompleteListener(it) {task->
				if(task.isSuccessful){
					Log.d(TAG, "signInWithCredential:success")
					val intent = Intent()
					activity?.setResult(Activity.RESULT_OK, intent)
				}
			}
		}
	}

	override fun handleLoginTap() {
		mViewModel.onLogin()
	}

	override fun handleFacebookTap() {
		val loginManager = LoginManager.getInstance()
		mCallBackManager = CallbackManager.Factory.create()

		loginManager.logInWithReadPermissions(this, Arrays.asList("email","public_profile"))
		loginManager.registerCallback(mCallBackManager, object:FacebookCallback<LoginResult>{
			override fun onCancel() {}

			override fun onSuccess(result: LoginResult?) {
				result?.let {
					handleFacebookToken(result.accessToken)
				}
			}

			override fun onError(error: FacebookException?) {
				ExceptionUtil.handleException(error)
				if (AndroidUtil.isDebuggable(context)) AndroidUtil.showErrorMessage(context, error)
				AndroidUtil.showMessage(mBinding.root, error?.message)
			}

		})
	}

	override fun handleGoogleTap() {

	}

	override fun onLoginSuccess() {

	}

	override fun handleRegisterTap() {

	}
	override fun onLoginError(throwable: Throwable) {
		if (isAdded) {
			ExceptionUtil.handleException(throwable)
			if (AndroidUtil.isDebuggable(context)) AndroidUtil.showErrorMessage(context, throwable)
			AndroidUtil.showMessage(mBinding.root, getString(R.string.message_data_fetch_failed))
		}
	}

	override fun handleForgetPasswordTap() {

	}

	override fun onEmailEmpty() {
		if(isAdded){
			mBinding.emailContainer.error = getString(R.string.lit_error_email_empty)
			mBinding.passwordContainer.error = null
		}
	}

	override fun onPasswordEmpty() {
		if(isAdded){
			mBinding.emailContainer.error = null
			mBinding.passwordContainer.error = getString(R.string.lit_error_password_empty)
		}
	}

	override fun onEmailNotMatchFormat() {
		if(isAdded){
			mBinding.emailContainer.error = getString(R.string.lit_error_email_does_not_match_format)
			mBinding.passwordContainer.error = null
		}
	}
}