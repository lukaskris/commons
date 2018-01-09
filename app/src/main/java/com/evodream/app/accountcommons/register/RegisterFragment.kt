package com.evodream.app.accountcommons.register

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.evodream.app.accountcommons.R
import com.evodream.app.accountcommons.base.BaseFragment
import com.evodream.app.accountcommons.databinding.RegisterBinding
import com.evodream.app.accountcommons.login.LoginFragment
import com.evodream.app.accountcommons.util.AndroidUtil
import dmax.dialog.SpotsDialog

/**
 * @author Lukaskris
 * @since 2018/01/07
 */
class RegisterFragment : BaseFragment(), RegisterHandler, RegisterNavigator {

    private lateinit var mBinding: RegisterBinding
    private lateinit var mViewModel: RegisterViewModel
    private lateinit var mProgresDialog: SpotsDialog

    companion object {
        @JvmStatic
        fun newInstance():Fragment{
            return RegisterFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.lit_register)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = inflater.inflate(R.layout.register, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel = RegisterViewModel(context!!, this)
        mBinding = DataBindingUtil.bind(view)
        mBinding.handler = this
        mBinding.viewModel = mViewModel
    }

    private fun showProgressDialog(){
        mProgresDialog = AndroidUtil.showProgressDialog(context)
        mProgresDialog.show()
    }

    private fun dismissProgressDialog(){
        if(mProgresDialog.isShowing) mProgresDialog.dismiss()
    }


    override fun onNameEmpty() {
        if(isAdded){
            mBinding.nameContainer.error = getString(R.string.lit_error_name_empty)
            mBinding.emailContainer.error = null
            mBinding.phoneContainer.error = null
            mBinding.passwordContainer.error = null
        }
    }

    override fun onEmailEmpty() {
        if(isAdded){
            mBinding.nameContainer.error = null
            mBinding.emailContainer.error = getString(R.string.lit_error_email_empty)
            mBinding.phoneContainer.error = null
            mBinding.passwordContainer.error = null
        }
    }

    override fun onEmailNotMatchFormat() {
        if(isAdded){
            mBinding.nameContainer.error = null
            mBinding.emailContainer.error = getString(R.string.lit_error_email_does_not_match_format)
            mBinding.phoneContainer.error = null
            mBinding.passwordContainer.error = null
        }
    }

    override fun onPasswordEmpty() {
        if(isAdded){
            mBinding.nameContainer.error = null
            mBinding.emailContainer.error = null
            mBinding.phoneContainer.error = null
            mBinding.passwordContainer.error = getString(R.string.lit_error_password_empty)
        }
    }

    override fun onPhoneEmpty() {
        if(isAdded){
            mBinding.nameContainer.error = null
            mBinding.emailContainer.error = null
            mBinding.phoneContainer.error = getString(R.string.lit_error_phone_empty)
            mBinding.passwordContainer.error = null
        }
    }

    override fun handleLoginClick() {
        AndroidUtil.switchTo(R.id.fragment_container, LoginFragment.newInstance(), fragmentManager)
    }

    override fun handleRegisterClick() {
        mViewModel.onRegister()
    }
}