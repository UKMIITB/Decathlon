package com.example.decathlon.homescreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.decathlon.R
import com.example.decathlon.homescreen.model.HomeItem
import com.example.decathlon.homescreen.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val bottomSheetUIState by homeViewModel.bottomSheetUIState.collectAsStateWithLifecycle()

    val searchQuery by homeViewModel.searchQuery.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = bottomSheetUIState.showBottomSheet) {
        when (bottomSheetUIState.showBottomSheet) {
            true -> scope.launch {
                bottomSheetState.show()
            }

            false -> scope.launch {
                bottomSheetState.hide()
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { bottomSheetState.currentValue }.collect {
            if (it == ModalBottomSheetValue.Hidden) {
                homeViewModel.updateBottomSheetUIState(
                    showBottomSheet = false
                )
            }
        }
    }

    ModalBottomSheetLayout(sheetState = bottomSheetState,
        sheetContent = {
            HomeScreenBottomSheetContent()
        }, content = {
            Scaffold(topBar = {
                TopBarContent(
                    onNavigationIconClicked = navigator::navigateUp,
                    onSearchIconClicked = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    },
                    onFilterIconClicked = {
                        homeViewModel.updateBottomSheetUIState(
                            showBottomSheet = true
                        )
                    },
                    searchQuery = searchQuery,
                    onSearchQueryChanged = homeViewModel::updateSearchQuery
                )
            },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        HomeScreenGridContent(homeViewModel = homeViewModel)
                    }
                }
            )
        })
}

@Composable
private fun TopBarContent(
    onNavigationIconClicked: () -> Unit,
    onSearchIconClicked: () -> Unit,
    onFilterIconClicked: () -> Unit,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = 0.75f))
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.outline_arrow_back_24),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
                .clickable { onNavigationIconClicked() })

        Spacer(modifier = Modifier.width(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "Search") },
            trailingIcon = {
                Icon(painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "",
                    modifier = Modifier.clickable { onSearchIconClicked() })
            },
            singleLine = true,
            maxLines = 1,
            shape = RoundedCornerShape(100.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearchIconClicked.invoke()
            }),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent.copy(
                    alpha = ContentAlpha.disabled
                )
            )
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(painter = painterResource(id = R.drawable.outline_sort_24),
            contentDescription = "",
            modifier = Modifier
                .size(32.dp)
                .clickable { onFilterIconClicked() })
    }
}

@Composable
private fun HomeScreenGridContent(homeViewModel: HomeViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(count = 2), content = {
        item {
            HomeScreenGridItem(homeItem = homeViewModel.homeItem1)
        }
        item {
            HomeScreenGridItem(homeItem = homeViewModel.homeItem2)
        }
    })
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun HomeScreenGridItem(homeItem: HomeItem) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .border(width = 1.dp, color = Color.LightGray.copy(alpha = 0.4f))
        .clickable { }) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(color = Color.LightGray.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            GlideImage(
                model = homeItem.imageUrl, contentDescription = "",
                loading = placeholder(R.drawable.outline_downloading_24),
                failure = placeholder(R.drawable.outline_sms_failed_24),
                modifier = Modifier.size(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = homeItem.name,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = homeItem.brand,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "$ ${homeItem.price}",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .background(color = Color.Yellow.copy(alpha = 0.5f))
                .padding(all = 4.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun HomeScreenBottomSheetContent() {
    BottomSheetSortContent()
}

@Composable
private fun BottomSheetSortContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        Text("SORT BY", fontWeight = FontWeight.Light)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Most Relevant")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Price: Low to High")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Price: High to Low")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Name: A to Z")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Name: Z to A")
    }
}