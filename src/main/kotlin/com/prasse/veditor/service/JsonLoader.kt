package com.prasse.veditor.service

import com.fasterxml.jackson.databind.JsonNode
import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.BasicJsonObject
import com.prasse.veditor.model.Clan
import com.prasse.veditor.model.Tugenden
import org.springframework.stereotype.Component

@Component
class JsonLoader(
    val jsonService: JsonService,
) {

    companion object KEYS {
        const val CLAN = "CLAN"
        const val TUGENDEN = "TUGENDEN"
    }

    fun loadDataWithDefault(jsonKey: String): Map<String, List<BasicJsonObject>> {
        return loadData<BasicJsonObject>(jsonKey)
    }

    fun <T : BasicJsonObject> loadData(jsonKey: String): Map<String, List<T>> {
        // Preperation
        jsonService.mapOfData.map { filesPerFolder ->
            val name = filesPerFolder.key // Foldername
            val talentFiles = filesPerFolder.value.filter { jsonFiles -> jsonService.isType(jsonFiles, jsonKey) }

            return when (jsonKey) {
                CLAN -> {
                    loadClan(files = talentFiles, name = name) as Map<String, List<T>>
                }
                TUGENDEN -> {
                    loadTugend(files = talentFiles, name = name) as Map<String, List<T>>
                }
                else -> {
                    loadBasic(files = talentFiles, name = name) as Map<String, List<T>>
                }
            }
        }
        return emptyMap()
    }

    private fun loadClan(
        files: List<JsonNode>,
        name: String,
    ): Map<String, List<Clan>> {
        val listOfClans = mutableListOf<Clan>()
        val outputMap = mutableMapOf<String, List<Clan>>()

        files.forEach { singleFile ->
            singleFile.get("data").map { jsonInput ->
                listOfClans.add(
                    Clan(id = jsonInput.get("id").intValue(),
                        name = jsonInput.get("name").textValue(),
                        weakness = jsonInput.get("weakness").textValue(),
                        disciplines = jsonInput.get("discipline").map { it.intValue() }
                    )
                )
            }
        }
        outputMap[name] = listOfClans
        return outputMap
    }

    private fun loadTugend(
        files: List<JsonNode>,
        name: String,
    ): Map<String, List<BasicJsonObject>>  {
        val outputMap = mutableMapOf<String, List<BasicJsonObject>>()
        val outputData = mutableListOf<BasicJsonObject>()
        files.forEach { singleFile ->
            singleFile.get("data").map { jsonInput ->
                val tugendData = jsonInput.get("data").map { td ->
                    BasicJsonObject(
                        id = td.get("id").intValue(),
                        name = td.get("name").textValue()
                    )
                }
                outputData.add(
                    Tugenden(
                        id = jsonInput.get("id").intValue(),
                        name = jsonInput.get("name").textValue(),
                        data = tugendData
                    )
                )
            }
        }
        outputMap[name] = outputData
        return outputMap
    }

    private fun loadBasic(
        files: List<JsonNode>,
        name: String,
    ): Map<String, List<BasicJsonObject>> {
        val outputMap = mutableMapOf<String, List<BasicJsonObject>>()
        val outputData = mutableListOf<BasicJsonObject>()
        files.forEach { singleFile ->
            singleFile.get("data").map { jsonInput ->
                outputData.add(BasicJsonObject(id = jsonInput.get("id").intValue(), name = jsonInput.get("name").textValue()))
            }
        }
        outputMap[name] = outputData
        return outputMap
    }
}