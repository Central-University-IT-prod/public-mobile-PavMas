package com.trifcdr.lifestylehub.presentation.fragments.profile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.trifcdr.lifestylehub.R
import com.trifcdr.lifestylehub.authorization.AuthResource
import com.trifcdr.lifestylehub.authorization.database.model.AuthUser
import com.trifcdr.lifestylehub.databinding.FragmintProfileBinding
import com.trifcdr.lifestylehub.domain.models.DomainResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Created by trifcdr.
 */
@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private lateinit var binding: FragmintProfileBinding
    private val vm: ProfileViewModel by viewModels()

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var login: EditText
    private lateinit var password: EditText
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var gender: EditText

    private lateinit var signInLogin: EditText
    private lateinit var signInPass: EditText

    private lateinit var btnLayout: LinearLayoutCompat
    private lateinit var signInLayout: LinearLayoutCompat
    private lateinit var signUpLayout: LinearLayoutCompat
    private lateinit var userViewLayout: LinearLayoutCompat


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmintProfileBinding.inflate(inflater, container, false)
        firstName = binding.firstNameEt
        lastName = binding.lastNameEt
        login = binding.loginEt
        password = binding.passEt
        phone = binding.phoneEt
        email = binding.emailEt
        firstName = binding.firstNameEt
        gender = binding.genderEt

        btnLayout = binding.buttonsLayout
        signInLayout = binding.signInLayout
        signUpLayout = binding.signUpLayout
        userViewLayout = binding.viewUserLayout

        signInLogin = binding.usernameEt
        signInPass = binding.passwordEt

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            vm.getCurrentUser()
                .collect{
                    when(it){
                        is AuthResource.Success -> {
                            showUser(vm.user!!)
                        }
                        else ->{
                            btnLayout.visibility = View.VISIBLE
                            registerClickListener()
                            loginClickListener()
                        }
                    }
                }
        }





    }

    private fun loginClickListener(){
        binding.login.setOnClickListener{
            btnLayout.visibility = View.INVISIBLE
            lifecycleScope.launch {
                signInLayout.visibility = View.VISIBLE
                binding.signInBtn.setOnClickListener {
                    if (signInLogin.text.toString() != "" && signInPass.text.toString() != "") {
                        lifecycleScope.launch {
                            vm.authenticateUser(
                                signInLogin.text.toString(),
                                signInPass.text.toString()
                            )
                                .collect {
                                    when (it){
                                        is AuthResource.Success ->{
                                            binding.signInLayout.visibility = View.INVISIBLE
                                            showUser(vm.user!!)
                                        }
                                        is AuthResource.Forbidden ->{
                                            Toast.makeText(context,
                                                getString(R.string.wrong), Toast.LENGTH_SHORT).show()
                                        }
                                        is AuthResource.UserDoesNotExist -> {
                                            Toast.makeText(context,
                                                getString(R.string.userDoesntExist), Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {Toast.makeText(context,
                                            getString(R.string.unknownError), Toast.LENGTH_SHORT).show()}
                                    }
                                }

                        }
                        signInLogin.setText("")
                        signInPass.setText("")
                    }
                }
            }
        }
    }

    private fun registerClickListener(){
        binding.register.setOnClickListener{
            binding.buttonsLayout.visibility = View.INVISIBLE
            lifecycleScope.launch {
                vm.getRandomUser()
                    .collect{ networkResource ->
                        if (networkResource is DomainResource.Success){
                            val res = networkResource.result
                            binding.signUpLayout.visibility = View.VISIBLE
                            firstName.setText(res.firstName)
                            lastName.setText(res.lastName)
                            email.setText(res.email)
                            phone.setText(res.phone)
                            gender.setText(res.gender)
                            login.setText(res.username)
                            password.setText("")
                            binding.createAccount.setOnClickListener {
                                lifecycleScope.launch {
                                    if(
                                        login.text.toString() != ""
                                        && password.text.toString() != ""
                                        && email.text.toString() != ""
                                        && phone.text.toString() != ""
                                        && firstName.text.toString() != ""
                                        && lastName.text.toString() != ""
                                        && gender.text.toString() != "")
                                    {
                                        vm.registerUser(
                                            AuthUser(
                                                login = login.text.toString(),
                                                password = password.text.toString(),
                                                email = email.text.toString(),
                                                phone = phone.text.toString(),
                                                gender = gender.text.toString(),
                                                firstName = binding.firstNameEt.text.toString(),
                                                lastName = binding.lastNameEt.text.toString(),
                                            )
                                        ).collect{
                                            when (it){
                                                is AuthResource.Success -> {
                                                    signUpLayout.visibility = View.INVISIBLE
                                                    showUser(it.result)
                                                }
                                                is AuthResource.UserDoesNotExist -> {
                                                    Toast.makeText(context,
                                                        getString(R.string.noUser), Toast.LENGTH_SHORT).show()
                                                }
                                                else -> {
                                                    Toast.makeText(context,
                                                        getString(R.string.unknownErr), Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                                    else{
                                        Toast.makeText(context,
                                            getString(R.string.empty), Toast.LENGTH_SHORT).show()
                                    }

                                }


                            }
                            binding.cancel.setOnClickListener {
                                signUpLayout.visibility = View.INVISIBLE
                                btnLayout.visibility = View.VISIBLE
                            }
                        }
                    }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showUser(user: AuthUser){
        userViewLayout.visibility = View.VISIBLE
        binding.firstNameTv.text = "First Name: ${user.firstName}"
        binding.lastNameTv.text = "Last Name: ${user.lastName}"
        binding.emailTv.text = "Email: ${user.email}"
        binding.phoneTv.text = "Phone: ${user.phone}"
        binding.genderTv.text = "Gender: ${user.gender}"
        binding.loginTv.text = "Login: ${user.login}"
        binding.logOutBtn.setOnClickListener{
            vm.deleteCurrentUser()
            userViewLayout.visibility = View.INVISIBLE
            btnLayout.visibility = View.VISIBLE
            registerClickListener()
            loginClickListener()

        }
    }
}