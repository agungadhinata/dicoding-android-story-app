package com.nekkiichi.storyapp.ui.view.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.nekkiichi.storyapp.data.ResponseStatus
import com.nekkiichi.storyapp.data.remote.response.FullAuthResponse
import com.nekkiichi.storyapp.databinding.ActivityLoginBinding
import com.nekkiichi.storyapp.ui.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    private var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setup binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.session.collect {
                        collectLoginState(it)
                    }
                }
            }
        }

        binding.btnToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener {
            //TODO: Add Loading State and intent listener to main app
            viewModel.logIn(binding.edLoginEmail.text.toString(), binding.edLoginPassword.text.toString())
        }
    }
    private fun collectLoginState(status: ResponseStatus<FullAuthResponse>) {
        when(status) {
            is ResponseStatus.loading -> showLoading(true)
            is ResponseStatus.Error -> {
                showLoading(false)
                Toast.makeText(this, "Error: ${status.error}", Toast.LENGTH_SHORT).show()
            }
            is ResponseStatus.Success -> {
                showLoading(false)
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                startActivity(intent)
            }
            else -> showLoading(false)
        }
    }
    private fun showLoading(bool: Boolean) {

    }
}