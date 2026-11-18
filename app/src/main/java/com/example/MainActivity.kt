package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ConnectWithoutContact
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.ConnectWithoutContact
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.components.BookDetailBottomSheet
import com.example.ui.components.BrandTopHeader
import com.example.ui.components.InsightDetailBottomSheet
import com.example.ui.components.ServiceDetailBottomSheet
import com.example.ui.navigation.AppRoutes
import com.example.ui.screens.ConnectScreen
import com.example.ui.screens.OverviewScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.screens.WebPortalScreen
import com.example.ui.screens.WorksScreen
import com.example.ui.theme.RakeshMishraTheme
import com.example.viewmodel.MainViewModel

private data class NavDestinationItem(
    val route: String,
    @StringRes val labelRes: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

private val PrimaryDestinations = listOf(
    NavDestinationItem(
        route = AppRoutes.ROUTE_OVERVIEW,
        labelRes = R.string.nav_overview,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    ),
    NavDestinationItem(
        route = AppRoutes.ROUTE_SERVICES,
        labelRes = R.string.nav_services,
        selectedIcon = Icons.Filled.AutoAwesome,
        unselectedIcon = Icons.Outlined.AutoAwesome
    ),
    NavDestinationItem(
        route = AppRoutes.ROUTE_WORKS,
        labelRes = R.string.nav_works,
        selectedIcon = Icons.Filled.MenuBook,
        unselectedIcon = Icons.Outlined.MenuBook
    ),
    NavDestinationItem(
        route = AppRoutes.ROUTE_PORTAL,
        labelRes = R.string.nav_portal,
        selectedIcon = Icons.Filled.Language,
        unselectedIcon = Icons.Outlined.Language
    ),
    NavDestinationItem(
        route = AppRoutes.ROUTE_CONNECT,
        labelRes = R.string.nav_connect,
        selectedIcon = Icons.Filled.ConnectWithoutContact,
        unselectedIcon = Icons.Outlined.ConnectWithoutContact
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val systemDark = isSystemInDarkTheme()
            val isDark = uiState.isDarkThemeOverride ?: systemDark

            RakeshMishraTheme(darkTheme = isDark) {
                RakeshMishraAppShell(
                    isDarkTheme = isDark,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun RakeshMishraAppShell(
    isDarkTheme: Boolean,
    viewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.statusBannerMessage) {
        val message = uiState.statusBannerMessage
        if (message != null) {
            snackbarHostState.showSnackbar(message)
            viewModel.setStatusBanner(null)
        }
    }

    fun navigateTopLevel(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= 600.dp

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentWindowInsets = WindowInsets.safeDrawing,
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            bottomBar = {
                if (!useNavigationRail) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 4.dp
                    ) {
                        PrimaryDestinations.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == item.route
                            } == true
                            NavigationBarItem(
                                selected = selected,
                                onClick = { navigateTopLevel(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = stringResource(item.labelRes)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(item.labelRes),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.testTag("bottom_nav_${item.route}")
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (useNavigationRail) {
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        PrimaryDestinations.forEach { item ->
                            val selected = currentDestination?.hierarchy?.any {
                                it.route == item.route
                            } == true
                            NavigationRailItem(
                                selected = selected,
                                onClick = { navigateTopLevel(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = stringResource(item.labelRes)
                                    )
                                },
                                label = {
                                    Text(
                                        text = stringResource(item.labelRes),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                colors = NavigationRailItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                modifier = Modifier.testTag("rail_nav_${item.route}")
                            )
                        }
                    }
                }

                Column(modifier = Modifier.fillMaxSize()) {
                    BrandTopHeader(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { viewModel.toggleTheme(isDarkTheme) },
                        onOpenLivePortal = { navigateTopLevel(AppRoutes.ROUTE_PORTAL) }
                    )

                    AppNavHost(
                        navController = navController,
                        viewModel = viewModel,
                        onNavigateTopLevel = ::navigateTopLevel,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }

    uiState.activeServiceDetail?.let { service ->
        ServiceDetailBottomSheet(
            service = service,
            onDismiss = { viewModel.openServiceDetail(null) },
            onRequestProposal = { title ->
                viewModel.preselectServiceForInquiry(title)
                navigateTopLevel(AppRoutes.ROUTE_CONNECT)
            }
        )
    }

    uiState.activeBookDetail?.let { book ->
        BookDetailBottomSheet(
            book = book,
            onDismiss = { viewModel.openBookDetail(null) },
            onExploreLiveSite = {
                viewModel.openBookDetail(null)
                navigateTopLevel(AppRoutes.ROUTE_PORTAL)
            }
        )
    }

    uiState.activeInsightDetail?.let { insight ->
        InsightDetailBottomSheet(
            insight = insight,
            onDismiss = { viewModel.openInsightDetail(null) },
            onOpenPortal = {
                viewModel.openInsightDetail(null)
                navigateTopLevel(AppRoutes.ROUTE_PORTAL)
            }
        )
    }
}

@Composable
private fun AppNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    onNavigateTopLevel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.ROUTE_OVERVIEW,
        modifier = modifier
    ) {
        composable(AppRoutes.ROUTE_OVERVIEW) {
            OverviewScreen(
                uiState = uiState,
                onBookConsultationClick = { onNavigateTopLevel(AppRoutes.ROUTE_CONNECT) },
                onOpenLivePortalClick = { onNavigateTopLevel(AppRoutes.ROUTE_PORTAL) },
                onOpenServicesClick = { onNavigateTopLevel(AppRoutes.ROUTE_SERVICES) },
                onOpenWorksClick = { onNavigateTopLevel(AppRoutes.ROUTE_WORKS) },
                onServiceSelected = { service -> viewModel.openServiceDetail(service) }
            )
        }

        composable(AppRoutes.ROUTE_SERVICES) {
            ServicesScreen(
                uiState = uiState,
                onCategorySelected = viewModel::selectCategory,
                onServiceSelected = viewModel::openServiceDetail
            )
        }

        composable(AppRoutes.ROUTE_WORKS) {
            WorksScreen(
                uiState = uiState,
                onBookSelected = viewModel::openBookDetail,
                onInsightSelected = viewModel::openInsightDetail
            )
        }

        composable(AppRoutes.ROUTE_PORTAL) {
            WebPortalScreen(
                portalState = uiState.portalState,
                onPageStarted = viewModel::onPortalPageStarted,
                onProgressChanged = viewModel::onPortalProgressChanged,
                onPageFinished = viewModel::onPortalPageFinished,
                onError = viewModel::onPortalError,
                onRetry = viewModel::resetPortalError
            )
        }

        composable(AppRoutes.ROUTE_CONNECT) {
            ConnectScreen(
                formState = uiState.consultationForm,
                onFullNameChanged = viewModel::updateFullName,
                onCompanyChanged = viewModel::updateCompanyName,
                onEmailChanged = viewModel::updateEmail,
                onToggleService = viewModel::toggleInquiryService,
                onSelectBudget = viewModel::selectBudgetTier,
                onNotesChanged = viewModel::updateProjectNotes,
                onBuildValidatedBrief = viewModel::buildValidatedInquiryBody,
                onShowMessage = viewModel::setStatusBanner,
                onOpenLivePortal = { onNavigateTopLevel(AppRoutes.ROUTE_PORTAL) }
            )
        }
    }
}
