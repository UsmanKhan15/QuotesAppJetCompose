package com.example.quotesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quotesapp.screens.QuoteDetail
import com.example.quotesapp.screens.QuoteListScreen
import com.example.quotesapp.ui.theme.QuotesAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            DataManager.loadAssetsFromFile(this@MainActivity)
        }
        setContent {
            app()
        }
    }
}

@Composable
fun app(){
    if(DataManager.isDataLoaded.value){
        if(DataManager.currentPage.value == Pages.LISTING)
            QuoteListScreen(data = DataManager.data) {
                DataManager.switchPages(it)
            }
        else
            DataManager.currentQuote?.let { QuoteDetail(quote = it) }
    }
    else
        Text(text = "Loading...")
}

enum class Pages{
    LISTING,
    DETAIL
}