package com.example.calefit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {

    private var binding by autoCleared<FragmentSignUpBinding>()

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

        checkInfo()
        signUpEmail(navController)
    }

    private fun checkInfo() {
        with(binding) {
            idTextInputEditText.addTextChangedListener { userInput ->
                if (userInput != null) {
                    when {
                        userInput.isEmpty() -> {
                            binding.idTextInputLayout.error = "아이디를 입력해주세요"
                            idFlag = false
                        }
                        !checkEmailRegex(userInput.toString()) -> {
                            binding.idTextInputLayout.error = "이메일 아이디 형식이 맞지 않습니다"
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
                            binding.passwordTextInputLayout.error = "비밀번호를 입력해 주세요"
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
                            binding.passwordRecheckTextInputLayout.error = "비밀번호를 재 입력 해주세요"
                            passwordCheckFlag = false
                        }
                        binding.passwordRecheckTextInputEditText.text !=
                                binding.passwordRecheckTextInputEditText.text
                                || binding.passwordRecheckTextInputEditText.text.isNullOrEmpty() -> {
                            binding.passwordRecheckTextInputLayout.error = "비밀번호가 일치하지 않습니다"
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

    private fun signUpEmail(navController: NavController) {
        binding.btnSignUpOk.setOnClickListener {
            navController.navigate(R.id.action_singUpFragment_to_loginFragment)
        }
    }

    companion object {
        private val emailValidation =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$".toRegex()
    }
}