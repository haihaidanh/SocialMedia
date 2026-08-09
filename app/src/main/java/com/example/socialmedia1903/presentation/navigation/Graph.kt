package com.example.socialmedia1903.presentation.navigation

sealed class Graph(val route: String) {

    data object Post :
        Graph("post_graph")

    data object Collection :
        Graph("collection_graph")

    data object Battle :
        Graph("battle_graph")
}