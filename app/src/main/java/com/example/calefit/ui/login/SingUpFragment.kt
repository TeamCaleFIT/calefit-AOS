package com.example.calefit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentSignUpBinding
import com.example.calefit.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {

    private var binding by autoCleared<FragmentSignUpBinding>()
    private val viewModel: LoginViewModel by activityViewModels()
    private var idFlag = false
    private var passwordFlag = false
    private var passwordCheckFlag = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        emailSignUpUserInfo()
        goBackToLogin(navController)
    }

    private fun emailSignUpUserInfo() {
        with(binding) {
            idTextInputEditText.addTextChangedListener { userInput ->
                if (userInput != null) {
                    when {
                        userInput.isEmpty() -> {
                            binding.idTextInputLayout.error =
                                getString(R.string.user_email_zone)
                            idFlag = false
                        }
                        !checkEmailRegex(userInput.toString()) -> {
                            binding.idTextInputLayout.error =
                                getString(R.string.not_validate_email_format)
                            idFlag = false
                        }
                        else -> {
                            binding.idTextInputLayout.error = null
                            idFlag = true
                        }
                    }
                }
                checkFlags()
            }
            passwordTextInputEditText.addTextChangedListener { userInput ->
                if (userInput != null) {
                    when {
                        userInput.isEmpty() -> {
                            binding.passwordTextInputLayout.error =
                                getString(R.string.user_password_zone)
                            passwordFlag = false
                        }
                        else -> {
                            binding.passwordTextInputLayout.error = null
                            passwordFlag = true
                        }
                    }
                }
                checkFlags()
            }
            passwordRecheckTextInputEditText.addTextChangedListener { userInput ->
                if (userInput != null) {
                    when {
                        userInput.isEmpty() -> {
                            binding.passwordRecheckTextInputLayout.error =
                                getString(R.string.user_password_not_match)
                            passwordCheckFlag = false
                        }
                        binding.passwordRecheckTextInputEditText.text !=
                                binding.passwordRecheckTextInputEditText.text
                                || binding.passwordRecheckTextInputEditText.text.isNullOrEmpty() -> {

                            binding.passwordRecheckTextInputLayout.error =
                                getString(R.string.user_password_diff)
                            passwordCheckFlag = false
                        }
                        else -> {
                            binding.passwordRecheckTextInputLayout.error = null
                            passwordCheckFlag = true
                        }
                    }
                }
                checkFlags()
            }
        }
    }


    private fun checkEmailRegex(id: String): Boolean {
        return emailValidation.matches(id)
    }

    private fun checkFlags() {
        binding.btnSignUpOk.isEnabled = idFlag && passwordFlag && passwordCheckFlag
    }

    private fun goBackToLogin(navController: NavController) {
        binding.btnSignUpOk.setOnClickListener {
            navController.navigate(R.id.action_singUpFragment_to_loginFragment)
        }
    }

    companion object {
        private val emailValidation =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
    }
}