package com.prasse.veditor.service

import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.Clan
import org.springframework.stereotype.Service

@Service
class ClanLoaderService(
    private val jsonService: JsonService
) {


    fun loadClans() : Map<String,List<Clan>>{
        // Preperation
        val map = mutableMapOf<String, List<Clan>>()
        val listOfTalents = mutableListOf<Clan>()
        jsonService.mapOfData.map { filesPerFolder ->
            val name = filesPerFolder.key // Foldername
            val clanFiles = filesPerFolder.value.filter { jsonFiles -> jsonService.isType(jsonFiles,"CLANS")}
            clanFiles.forEach { singleFile ->
                singleFile.get("data").map { data ->
                    listOfTalents.add(
                        Clan(id = data.get("id").intValue(),
                            name= data.get("name").textValue(),
                            weakness = data.get("weakness").textValue(),
                            disciplines = data.get("discipline").map { it.intValue() }
                        )
                    )
                }
            }
            map.put(name, listOfTalents)
        }

        return map
    }
}