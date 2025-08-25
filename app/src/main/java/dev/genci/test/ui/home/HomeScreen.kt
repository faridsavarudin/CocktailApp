package dev.genci.test.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dev.genci.test.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onItemClick: (String) -> Unit
) {
    val cocktails by viewModel.cocktails.observeAsState(emptyList())
    var text by remember { mutableStateOf("") }
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val lastSearch by viewModel.lastSearch.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cocktail App") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {

            OutlinedTextField(
                value = lastSearch,
                onValueChange = {
                    text = it
                    viewModel.onQueryChange(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                singleLine = true,
                label = { Text("Search cocktails") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    if (lastSearch.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.onQueryChange("")
                        }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Clear search"
                            )
                        }
                    }
                }
            )

            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.refresh(text) },
                modifier = Modifier.fillMaxSize()
            ) {
                if (cocktails.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "No data available",
                                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(cocktails, key = { it.id }) { item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemClick(item.id) },
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Thumbnail gambar cocktail
                                    AsyncImage(
                                        model = item.thumb,
                                        contentDescription = item.name,
                                        placeholder = painterResource(R.drawable.default_image),
                                        error = painterResource(R.drawable.default_image),
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(
                                            item.name,
                                            style = MaterialTheme.typography.titleMedium,
                                            maxLines = 1
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            item.category ?: "",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}