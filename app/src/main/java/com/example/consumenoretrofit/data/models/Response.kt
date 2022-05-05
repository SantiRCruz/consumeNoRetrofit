package com.example.consumenoretrofit.data.models

data class Response(
    val page:Int = 0,
    val per_page:Int = 6,
    val total:Int = 12,
    val total_pages:Int = 12,
    val data:List<Data> = listOf(),
)
