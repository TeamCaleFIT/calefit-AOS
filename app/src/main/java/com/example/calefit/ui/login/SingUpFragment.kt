package com.example.calefit.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.common.repeatOnLifecycleExtension
import com.example.calefit.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {

    private var binding by autoCleared<FragmentSignUpBinding>()
    private val viewModel: SignUpViewModel by viewModels()

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
        binding.viewModel = viewModel

        observeViewModel()
        goBackToLogin(navController)
    }

    private fun observeViewModel() {
        with(viewLifecycleOwner) {
            repeatOnLifecycleExtension {
                viewModel.hasRequiredSignUpData.collect {
                    binding.isFilled = it
                }
            }
            repeatOnLifecycleExtension {
                viewModel.userEmail.collect {
                    if (viewModel.checkEmailAddress()) {
                        binding.idTextInputLayout.error = null
                    } else {
                        binding.idTextInputLayout.error =
                            getString(R.string.not_validate_email_format)
                    }
                }
            }
            repeatOnLifecycleExtension {
                viewModel.userPasswordCheck.collect {
                    if (viewModel.checkPassword()) {
                        binding.passwordRecheckTextInputLayout.error = null
                    } else {
                        binding.passwordRecheckTextInputLayout.error =
                            getString(R.string.user_password_diff)
                    }
                }
            }
        }
    }

    //TODO check if email sign up is validated from the server then change fragment to another
    private fun goBackToLogin(navController: NavController) {
        binding.btnSignUpOk.setOnClickListener {
            navController.navigate(R.id.action_singUpFragment_to_loginFragment)
        }
    }
}
