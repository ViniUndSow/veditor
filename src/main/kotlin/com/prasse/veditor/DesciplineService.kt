package com.prasse.veditor

import com.fasterxml.jackson.databind.JsonNode
import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.Discipline
import com.prasse.veditor.model.Talent
import org.springframework.stereotype.Service

@Service
class DesciplineService(
    val jsonService: JsonService
) {

    fun loadTalents() : Map<String,List<Discipline>>{
        // Preperation
        val map = mutableMapOf<String, List<Discipline>>()
        val listOfTalents = mutableListOf<Discipline>()
        jsonService.mapOfData.map { filesPerFolder ->
            val name = filesPerFolder.key // Foldername
            val talentFiles = filesPerFolder.value.filter { jsonFiles -> jsonService.isType(jsonFiles,"DISCIPLINE")}
            talentFiles.forEach { singleFile ->
                singleFile.get("data").map { data ->
                    listOfTalents.add(Discipline(id = data.get("id").intValue(), name= data.get("name").textValue()))
                }
            }
            map.put(name, listOfTalents)
        }

        return map
    }
}