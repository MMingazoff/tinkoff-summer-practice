package com.itis.tinkoff.ui.screens.home.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.itis.tinkoff.R
import com.itis.tinkoff.domain.models.ProductModel
import com.itis.tinkoff.ui.base.CustomTextField
import com.itis.tinkoff.ui.base.DoneButton
import com.itis.tinkoff.ui.base.Toolbar
import com.itis.tinkoff.ui.base.cachingImage
import com.itis.tinkoff.ui.navigation.HomeScreen
import com.itis.tinkoff.ui.theme.base.Theme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    Surface(color = Theme.colors.primaryBackground) {
        Column {
            Toolbar(text = R.string.home)
            FiltersBottomSheet(
                state = state.value,
                eventHandler = viewModel::event,
                viewModel = viewModel
            )
        }
        HomeActions(
            navController = navController,
            action = action
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeContent(
    state: HomeState,
    eventHandler: (HomeEvent) -> Unit,
    viewModel: HomeViewModel,
    bottomSheetState: ModalBottomSheetState,
) {
    val scope = rememberCoroutineScope()

    Column {
        SearchBar(viewModel = viewModel)
        Spacer(modifier = Modifier.height(4.dp))
        ExtendedFloatingActionButton(
            modifier = Modifier
                .height(40.dp)
                .align(Alignment.End)
                .padding(end = 8.dp),
            text = { Text(stringResource(R.string.filters)) },
            icon = { Icon(Icons.Filled.KeyboardArrowRight, contentDescription = null) },
            onClick = { scope.launch { bottomSheetState.show() } },
            containerColor = Theme.colors.tintColor,
            contentColor = Theme.colors.onTintColor,
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    color = Theme.colors.primaryText
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(),
                columns = GridCells.Adaptive(minSize = 164.dp)
            ) {
                items(state.filteredProducts, key = { it.id }) { product ->
                    Product(product = product) {
                        eventHandler(HomeEvent.OnProductClick(product.id))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
private fun Product(product: ProductModel, onClick: (ProductModel) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .clickable {
                onClick(product)
            },
        elevation = 8.dp,
        backgroundColor = Theme.colors.secondaryBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = cachingImage(url = product.photo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                color = Theme.colors.primaryText,
                style = Theme.typography.body
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.price.toString(),
                color = Theme.colors.primaryText,
                style = Theme.typography.body,
            )
        }
    }
}

@Composable
private fun SearchBar(viewModel: HomeViewModel) {
    var query by rememberSaveable { mutableStateOf("") }
    CustomTextField(
        value = query,
        label = R.string.search_label,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        query = it
        viewModel.onQueryChanged(it)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun FiltersBottomSheet(
    state: HomeState,
    eventHandler: (HomeEvent) -> Unit,
    viewModel: HomeViewModel,
) {
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetContent = {
            FiltersBottomSheetContent(
                state = state,
                eventHandler = eventHandler,
            )
        },
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        HomeContent(state, eventHandler, viewModel, sheetState)
    }
}

@Composable
private fun FiltersBottomSheetContent(
    state: HomeState,
    eventHandler: (HomeEvent) -> Unit,
) {
    Surface(color = Theme.colors.primaryBackground) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(id = R.string.categories),
                style = Theme.typography.body,
                modifier = Modifier.padding(8.dp)
            )
            FiltersChipGroup(
                filters = state.categories,
                selectedFilters = state.selectedCategories,
                onSelectedChanged = { category -> eventHandler(HomeEvent.AddCategoryFilter(category)) }
            )
            Text(
                text = stringResource(id = R.string.sellers),
                style = Theme.typography.body,
                modifier = Modifier.padding(8.dp),
            )
            FiltersChipGroup(
                filters = state.sellers.map { it.name },
                selectedFilters = state.selectedSellers,
                onSelectedChanged = { seller -> eventHandler(HomeEvent.AddSellerFilter(seller)) }
            )
            var minPrice by rememberSaveable { mutableStateOf(1f) }
            var maxPrice by rememberSaveable { mutableStateOf(1000f) }
            Text(
                text = stringResource(id = R.string.price_range),
                style = Theme.typography.body,
                modifier = Modifier.padding(8.dp),
            )
            PriceRangeSlider(
                modifier = Modifier.padding(horizontal = 24.dp),
                values = minPrice..maxPrice
            ) { range ->
                minPrice = range.start
                maxPrice = range.endInclusive
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                DoneButton(
                    text = R.string.apply,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    eventHandler(
                        HomeEvent.ApplyFilters(minPrice.toInt()..maxPrice.toInt())
                    )
                }
                DoneButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    text = R.string.reset_all,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Theme.colors.secondaryBackground,
                        contentColor = Theme.colors.secondaryText,
                    )
                ) {
                    eventHandler(HomeEvent.ResetFilters)
                }
            }
        }
    }
}

@Composable
private fun PriceRangeSlider(
    modifier: Modifier = Modifier,
    values: ClosedFloatingPointRange<Float>,
    onPriceRangeChanged: (ClosedFloatingPointRange<Float>) -> Unit
) {
    RangeSlider(
        modifier = modifier,
        value = values,
        onValueChange = onPriceRangeChanged,
        // TODO change max
        valueRange = 1f..10_000_000f,
    )
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Text(
            text = stringResource(id = R.string.from) + "${values.start.toInt()}",
            style = Theme.typography.body,
        )
        Text(
            text = stringResource(id = R.string.to) + "${values.endInclusive.toInt()}",
            style = Theme.typography.body,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FiltersChipGroup(
    filters: List<String>,
    selectedFilters: Set<String>,
    onSelectedChanged: (String) -> Unit = {},
) {
    Column(modifier = Modifier.padding(8.dp)) {
        FlowRow {
            filters.forEach { filter ->
                Chip(
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
                    name = filter,
                    isSelected = selectedFilters.contains(filter),
                    onSelectionChanged = {
                        onSelectedChanged(it)
                    },
                )
            }
        }
    }
}

@Composable
private fun Chip(
    modifier: Modifier,
    name: String,
    isSelected: Boolean = false,
    onSelectionChanged: (String) -> Unit = {},
) {
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = if (isSelected) Theme.colors.tintColor else Theme.colors.secondaryBackground,
            contentColor = if (isSelected) Theme.colors.onTintColor else Theme.colors.primaryText
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = {
            onSelectionChanged(name)
        }
    ) {
        Text(text = name)
    }
}

@Composable
private fun HomeActions(
    navController: NavController,
    action: HomeAction?,
) {
    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            is HomeAction.Navigate -> {
                navController.navigate(HomeScreen.ProductDetails.createRoute(action.productId))
            }
        }
    }
}
