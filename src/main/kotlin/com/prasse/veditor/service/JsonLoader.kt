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
        const val CLAN = "CLANS"
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
                    Clan(id = getId(name),
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
                    val name = td.get("name").textValue()
                    BasicJsonObject(
                        id = getId(name),
                        name = name
                    )
                }
                val jsonInputName = jsonInput.get("name").textValue()
                outputData.add(
                    Tugenden(
                        id = getId(jsonInputName),
                        name = jsonInputName,
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
                val inputName = jsonInput.get("name").textValue()
                outputData.add(BasicJsonObject(id = getId(inputName), name = inputName))
            }
        }
        outputMap[name] = outputData
        return outputMap
    }

    private fun getId(name: String): String {
        return name.replace(" ","_").uppercase()
    }
}