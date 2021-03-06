package com.specikman.petbest.domain.use_case.login_use_cases.register

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.repository.RegisterRepository
import com.specikman.petbest.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        phone: String,
        name: String,
        context: Context,
        navController: NavController
    ) : Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading<String>())
            repository.register(email = email, password = password , phone = phone, name = name)
            emit(Resource.Success<String>("Logged in"))
            Toast.makeText(context,"Tạo tài khoản thành công",
                Toast.LENGTH_LONG).show()
            navController.navigate(Screen.MainScreen.route)
        } catch (e: Exception) {
            emit(Resource.Error<String>(e.localizedMessage ?: "An unexpected error occurred"))
            Toast.makeText(context,"Email hiện tại đã sử dụng ở tài khoản khác. Vui lòng chọn email mới",
                Toast.LENGTH_LONG).show()
        }
    }
}