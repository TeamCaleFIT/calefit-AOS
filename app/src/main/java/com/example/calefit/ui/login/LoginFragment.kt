package com.example.calefit.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.calefit.R
import com.example.calefit.common.autoCleared
import com.example.calefit.databinding.FragmentLoginBinding
import com.example.calefit.ui.home.HomeActivity
import com.example.calefit.ui.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment @Inject constructor() : Fragment() {

    private var binding by autoCleared<FragmentLoginBinding>()

    private val viewModel: LoginViewModel by activityViewModels()

    private val intent: Intent by lazy {
        Intent(context, HomeActivity::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navigationController = findNavController()

        gotoSignUp(navigationController)
        gotoHomeActivity()
    }

    private fun gotoSignUp(navController: NavController) {
        with(binding) {
            btnLogin.setOnClickListener {
                startActivity(intent)
            }
            tvEmailSignUp.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_singUpFragment)
            }
        }
    }

    /**
     *This function will be changed when a server is connected
     */
    private fun gotoHomeActivity() {
        binding.btnLogin.setOnClickListener {
            startActivity(intent)
        }
    }
}