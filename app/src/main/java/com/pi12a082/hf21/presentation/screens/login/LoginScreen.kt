package com.pi12a082.hf21.presentation.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.pi12a082.hf21.R
import com.pi12a082.hf21.domain.model.LoginCredentials
import com.pi12a082.hf21.navigation.Screen
import com.pi12a082.hf21.presentation.components.Btn
import com.pi12a082.hf21.presentation.components.ButtonLoading
import com.pi12a082.hf21.presentation.components.MyTextField
import com.pi12a082.hf21.presentation.components.PasswordInput

@Composable
fun LoginScreen(
    navController: NavController,
    loginScreenViewModel: LoginScreenViewModel = hiltViewModel(),
) {
    val uiState by loginScreenViewModel.uiState.collectAsState()
    val loginCredentials = LoginCredentials(email = uiState.emailInput,
        password = uiState.passwordInput)

    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = true
        )
    }

    LaunchedEffect(key1 = uiState.isLoading) {
        if (!uiState.isLoading && uiState.result.isNotEmpty() && uiState.errorMessage.isEmpty()) {
            Toast.makeText(context,
                "User ${uiState.result} login successful",
                Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            navController.navigate(Screen.Shop.route) { launchSingleTop = true }
        } else if (uiState.errorMessage.isNotEmpty()) {
            Toast.makeText(context, "Error ${uiState.errorMessage}", Toast.LENGTH_SHORT).show()
        }
    }


    Scaffold(
        content = { paddingValues ->  // ここでpaddingValuesを受け取る
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(paddingValues)  // paddingValuesを適用
                    .background(Color(0xfffcfcfc)),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 62.dp),
                    painter = painterResource(id = R.drawable.ic_origami_bird),
                    contentDescription = stringResource(id = R.string.logo)
                )
                Text(
                    text = "Login", style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Start
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp, top = 6.dp),
                    text = "Enter your email and password",
                    style = MaterialTheme.typography.h3,
                    color = Color(0xff727272),
                    textAlign = TextAlign.Start
                )
                MyTextField(
                    onInputChanged = loginScreenViewModel::onEmailInputChanged,
                    inputText = uiState.emailInput,
                    name = "Email"
                )
                PasswordInput(
                    onInputChanged = loginScreenViewModel::onPasswordInputChanged,
                    inputText = uiState.passwordInput,
                    showPassword = uiState.showPassword,
                    toggleShowPassword = loginScreenViewModel::toggleShowPassword,
                    name = "Password"
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    text = "Forgot Password?", style = MaterialTheme.typography.h6,
                )
                ButtonLoading(
                    name = "Login",
                    isLoading = uiState.isLoading,
                    enabled = uiState.emailInput.isNotEmpty() && uiState.passwordInput.isNotEmpty(),
                    onClicked = { loginScreenViewModel.loginUser(loginCredentials) }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Btn(
                    id = R.drawable.ic_google,
                    text = "Continue with Google",
                    clicked = {
                        Toast.makeText(context, "Feature not added", Toast.LENGTH_SHORT).show()
                    },
                    color = Color(0xff5383ec)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.h6,
                        fontFamily = FontFamily(
                            Font(R.font.gilroysemibold, weight = FontWeight.SemiBold)
                        ),
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            navController.navigate(Screen.Shop.route) { launchSingleTop = true }
                        }) {
                        Text(
                            text = "Signup",
                            style = MaterialTheme.typography.h6,
                            fontFamily = FontFamily(
                                Font(R.font.gilroysemibold, weight = FontWeight.SemiBold)
                            ),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        }
    )
}




