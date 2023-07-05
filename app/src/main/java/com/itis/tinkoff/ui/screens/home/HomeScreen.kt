package com.itis.tinkoff.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.ui.theme.base.Theme

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground) {
        HomeContent(state = state.value, eventHandler = viewModel::event, viewModel = viewModel)
        HomeActions(
            navController = navController,
            action = action
        )
    }
}

@Composable
fun HomeContent(
    state: HomeState,
    eventHandler: (HomeEvent) -> Unit,
    viewModel: HomeViewModel,
) {
    Column {
        SearchBar(viewModel)
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 164.dp)
        ) {
            items(state.filteredProducts, key = { it.id }) { product ->
                Product(product = product, onClick = { })
                Spacer(modifier = Modifier.height(4.dp))
            }
            if (!state.isLastPage) {
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp),
                            color = Theme.colors.primaryText
                        )
                        eventHandler(HomeEvent.LoadMore)
                    }
                }
            }
        }
    }
}

@Composable
fun Product(product: ProductModel, onClick: (ProductModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp)
            .clickable {
                onClick(product)
            },
        elevation = 8.dp,
        backgroundColor = Theme.colors.secondaryBackground,
        shape = Theme.shapes.cornersStyle
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = cachingImage(url = product.photo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = Theme.shapes.cornersStyle),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                color = Theme.colors.primaryText,
                style = Theme.typography.body
            )
            Text(
                text = product.price.toString(),
                color = Theme.colors.primaryText,
                style = Theme.typography.caption
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(viewModel: HomeViewModel) {
    var query by rememberSaveable { mutableStateOf("") }
    TextField(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = Theme.shapes.cornersStyle)
            .fillMaxWidth(),
        value = query,
        onValueChange = {
            query = it
            viewModel.onQueryChanged(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        label = { androidx.compose.material3.Text(stringResource(R.string.search_label)) }
    )
}

@Composable
fun HomeActions(
    navController: NavController,
    action: HomeAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            else -> {}
        }
    }
}

@Composable
fun cachingImage(url: String) = ImageRequest.Builder(LocalContext.current)
    .data(url)
    .crossfade(true)
    .memoryCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .build()
