package com.example.uas_pam.ui.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.uas_pam.R
import com.example.uas_pam.ui.CustomWidget.CustomTopAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppView(
    modifier: Modifier = Modifier,
    onNavigateAddSup: () -> Unit,
    onNavigateAddBrg: () -> Unit,
    onNavigateMenuBuku: () -> Unit,
    onNavigateListBrg: () -> Unit,
){
    Scaffold (
        modifier = modifier,
        topBar ={
            CustomTopAppBar(
                title = "Home",
                canNavigateBack = false,
                navigateUp = {},
                onRefresh = {},
            )
        }
    ){ innerPadding ->


        BodyHome(
            modifier = Modifier.padding(innerPadding),
            onBrgListClick ={
                onNavigateListBrg()
            },
            onAddSupClick = {
                onNavigateAddSup()
            },
            onMenuBukuClick = {
                onNavigateMenuBuku()
            },
            onAddBrgClick = {
                onNavigateAddBrg()
            }
        )
    }
}

@Composable
fun BodyHome(
    modifier: Modifier = Modifier,
    onBrgListClick: () -> Unit,
    onAddSupClick: () -> Unit,
    onAddBrgClick: () -> Unit,
    onMenuBukuClick: () -> Unit
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth().padding(16.dp)
    ){
        Row (
        ){
            CardMenu(
                namaMenu = "List Produk",
                onClick = onBrgListClick,
                namaGambar =  R.drawable.listproduk,
            )
            CardMenu(
                namaMenu = "Add Product",
                onClick = onAddBrgClick,
                namaGambar = R.drawable.listproduk
            )
        }
        Row(
        ) {
            CardMenu(
                namaMenu = "Menu Buku",
                onClick = onMenuBukuClick,
                namaGambar = R.drawable.listproduk
            )
            CardMenu(
                namaMenu = "AddSupplier",
                onClick = onAddSupClick,
                namaGambar =  R.drawable.listproduk
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMenu(
    namaMenu: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    namaGambar: Int
) {
    Box(
        modifier = modifier.padding(2.dp).background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
    ){
        Card(
            onClick = onClick,
            modifier = modifier
                .width(180.dp)
                .height(230.dp)
                .padding(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .background(color = Color(0xFF42A5F5)) // Cyan color
                    .fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Image(
                        painter = painterResource(id = namaGambar),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(
                        text = namaMenu,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }

}