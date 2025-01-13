package com.prasse.veditor.model

class Clan(
    val disciplines: List<Int>,
    val weakness: String,
    id: Int,
    name: String,
) : BasicJsonObject(id, name)