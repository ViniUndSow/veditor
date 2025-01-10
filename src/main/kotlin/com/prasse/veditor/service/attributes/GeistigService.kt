package com.prasse.veditor.service.attributes

import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.Talent
import org.springframework.stereotype.Service

@Service
class GeistigService(
    val jsonService: JsonService
) {

    fun load() : Map<String,List<Talent>>{
        // Preperation
        val map = mutableMapOf<String, List<Talent>>()
        val listOfTalents = mutableListOf<Talent>()
        jsonService.mapOfData.map { filesPerFolder ->
            val name = filesPerFolder.key // Foldername
            val talentFiles = filesPerFolder.value.filter { jsonFiles -> jsonService.isType(jsonFiles,"SPIRITUAL")}
            talentFiles.forEach { singleFile ->
                singleFile.get("data").map { data ->
                    listOfTalents.add(Talent(id = data.get("id").intValue(), name= data.get("name").textValue()))
                }
            }
            map.put(name, listOfTalents)
        }

        return map
    }
}