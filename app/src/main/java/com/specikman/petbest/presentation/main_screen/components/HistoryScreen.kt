package com.specikman.petbest.presentation.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.specikman.petbest.domain.model.Product
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel

@Composable
fun HistoryScreen(
    imageViewModel: ImageViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    LazyColumn(
        Modifier.padding(top = 16.dp)
    ){
        item{
            if(!homeViewModel.stateProducts.value.isLoading){
                val history = homeViewModel.stateHistory.value.history.sortedByDescending { it.date }
                val products = mutableListOf<Product>()
                history.forEach { tmp ->
                    products.add(homeViewModel.stateProducts.value.products.first { it.id == tmp.productId })
                }
                HistoryHeader()
                Products(products = products, viewModel = homeViewModel, navController = navController, imageViewModel = imageViewModel)
            }
        }
    }
}
@Composable
fun HistoryHeader() {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Lịch sử truy cập", fontSize = 30.sp, fontWeight = FontWeight.Bold)
    }
}