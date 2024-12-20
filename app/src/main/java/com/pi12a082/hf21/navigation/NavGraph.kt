package com.pi12a082.hf21.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.pi12a082.hf21.R
import com.pi12a082.hf21.domain.model.ProductDetail
import com.pi12a082.hf21.presentation.screens.bottom_nav_screens.account.AccountScreen
import com.pi12a082.hf21.presentation.screens.bottom_nav_screens.cart.MyCart
import com.pi12a082.hf21.presentation.screens.bottom_nav_screens.detail.ProductDetailScreen
import com.pi12a082.hf21.presentation.screens.bottom_nav_screens.explore.ExploreScreen
import com.pi12a082.hf21.presentation.screens.bottom_nav_screens.favourite.FavouriteScreen
import com.pi12a082.hf21.presentation.screens.bottom_nav_screens.shop.ShopScreen
import com.pi12a082.hf21.presentation.screens.login.LoginScreen
import com.pi12a082.hf21.presentation.screens.order.OrderScreen
import com.pi12a082.hf21.presentation.screens.signup.SignupScreen
import com.pi12a082.hf21.presentation.screens.splash.SplashScreen
import com.pi12a082.hf21.presentation.screens.welcome.GetStartedScreen
import com.pi12a082.hf21.presentation.screens.welcome.OnBoardingScreen
import com.pi12a082.hf21.util.Constants.DETAIL_ARGUMENT_KEY

@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun SetUpNavGraph(navController: NavHostController) {
    Scaffold(
        bottomBar = { MyBottomNav(navController = navController) }
    ) { paddingValues -> // contentPaddingを受け取る
        // NavHostにcontentPaddingを適用
        NavHost(navController = navController, startDestination = Screen.Splash.route, modifier = Modifier.padding(paddingValues)) {
            composable(route = Screen.Splash.route) { SplashScreen(navController = navController) }
            composable(route = Screen.Login.route) { LoginScreen(navController = navController) }
            composable(route = Screen.Welcome.route) { OnBoardingScreen(navController = navController) }
            composable(route = Screen.Shop.route) { ShopScreen(navController) }
            composable(route = Screen.Explore.route) { ExploreScreen() }
            composable(route = Screen.Cart.route) { MyCart(navController) }
            composable(route = Screen.Favourite.route) { FavouriteScreen() }
            composable(route = Screen.Account.route) { AccountScreen(navController = navController) }
            composable(route = Screen.Order.route) { OrderScreen(navController = navController) }
            composable(route = Screen.Start.route) { GetStartedScreen(navController = navController) }
            composable(route = "${Screen.Detail.route}/{$DETAIL_ARGUMENT_KEY}") {
                val product =
                    navController.previousBackStackEntry?.arguments?.getParcelable<ProductDetail>(DETAIL_ARGUMENT_KEY)
                if (product != null) {
                    ProductDetailScreen(navController = navController, product = product)
                }
            }
            composable(route = Screen.SignUp.route) {
                SignupScreen(navController = navController)
            }
        }
    }
}



@Composable
fun MyBottomNav(navController: NavHostController) {
    val bottomItems = listOf(
        Screen.Shop,
        Screen.Explore,
        Screen.Cart,
        Screen.Favourite,
        Screen.Account
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    if (currentDestination?.route in bottomItems.map { item -> item.route }
    ) {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 8.dp,
            modifier = Modifier.height(62.dp)
        ) {

            bottomItems.forEach { screen ->
                if (currentDestination != null) {
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(screen.drawableId!!),
                                contentDescription = stringResource(R.string.screen_label)
                            )
                        },
                        label = {
                            Text(
                                stringResource(screen.resourceId!!),
                                color = if (currentDestination.hierarchy.any {
                                        it.route == screen.route
                                    })
                                    MaterialTheme.colors.primary else Color.Black,
                                textAlign = TextAlign.Start)
                        },
                        selected = currentDestination.hierarchy.any { it.route == screen.route },
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        alwaysShowLabel = true,
                        selectedContentColor = MaterialTheme.colors.primary,
                        unselectedContentColor = Color.Black,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    } else {
        Spacer(modifier = Modifier.width(0.dp))
    }
}