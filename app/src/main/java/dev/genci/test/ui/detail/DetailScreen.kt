package dev.genci.test.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onBackClick: () -> Unit
) {
    val cocktail = viewModel.cocktail.observeAsState().value

    Scaffold(
        topBar = {
            CocktailDetailTopBar(
                title = cocktail?.name.orEmpty(),
                onBackClick =  onBackClick
            )
        }
    ) { padding ->
        if (cocktail == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Image
                AsyncImage(
                    model = cocktail.thumb,
                    contentDescription = cocktail.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(20.dp))

                )
                Spacer(Modifier.height(16.dp))

                // Name
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(Modifier.height(8.dp))

                // Category & Alcoholic
                Column(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Category: ${cocktail.category ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Glass: ${cocktail.strGlass ?: "-"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = cocktail.instructions ?: "-",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}