package com.prasse.veditor.model

class Clan(
    val disciplines: List<Int>,
    val weakness: String,
    id: String,
    name: String,
) : BasicJsonObject(id, name)