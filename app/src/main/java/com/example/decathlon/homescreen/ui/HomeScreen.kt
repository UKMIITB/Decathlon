package com.example.decathlon.homescreen.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.decathlon.R
import com.example.decathlon.homescreen.model.HomeItem
import com.example.decathlon.homescreen.viewmodel.HomeViewModel
import com.example.decathlon.util.noRippleClickable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@OptIn(ExperimentalMaterialApi::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(homeViewModel: HomeViewModel = hiltViewModel()) {

    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(sheetState = bottomSheetState,
        sheetContent = {

        }, content = {
            Scaffold(topBar = {
                TopBarContent(onCrossIconClicked = {},
                    onSearchIconClicked = {})
            },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        HomeScreenGridContent(homeViewModel = homeViewModel)
                    }
                },
                bottomBar = { BottomBarContent() })
        })
}

@Composable
private fun TopBarContent(
    onCrossIconClicked: () -> Unit,
    onSearchIconClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = 0.75f))
            .padding(vertical = 20.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.outline_close_24),
            contentDescription = "",
            modifier = Modifier.clickable { onCrossIconClicked() })

        Spacer(modifier = Modifier.weight(1f))

        Text(text = "Running Shoes")

        Spacer(modifier = Modifier.weight(1f))

        Icon(painter = painterResource(id = R.drawable.baseline_search_24),
            contentDescription = "",
            modifier = Modifier.clickable { onSearchIconClicked() })
    }
}

@Composable
private fun BottomBarContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray.copy(alpha = 0.75f))
            .padding(vertical = 20.dp, horizontal = 8.dp)
            .height(IntrinsicSize.Min)
    ) {
        BottomBarItem(
            modifier = Modifier.weight(1f),
            iconId = R.drawable.outline_sort_24,
            text = "SORT",
            onItemClicked = {}
        )

        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )

        BottomBarItem(
            modifier = Modifier.weight(1f),
            iconId = R.drawable.outline_filter_alt_24,
            text = "FILTER",
            onItemClicked = {}
        )
    }
}

@Composable
private fun BottomBarItem(
    modifier: Modifier, iconId: Int, text: String,
    onItemClicked: () -> Unit
) {
    Row(
        modifier = modifier.noRippleClickable { onItemClicked() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(id = iconId), contentDescription = "")

        Spacer(modifier = Modifier.width(4.dp))

        Text(text = text)
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