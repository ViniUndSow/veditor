package com.prasse.veditor.files

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.io.File

@Service
class JsonService(
    val folderService: FolderService
) {

    val jsonMapper = ObjectMapper()

    val mapOfData = mutableMapOf<String, List<JsonNode>>()

    companion object KEYS {
        val SKILLS = "SKILLS"
    }

    @PostConstruct
    fun loadAllJsonFiles() {
        File(FolderService.CUSTOM).listFiles()?.map { customFolder ->
            val folderName = customFolder.name
            val listOfJsonNodes = mutableListOf<JsonNode>()
            File(customFolder.path).listFiles().map { jsonFile ->
                listOfJsonNodes.add(jsonMapper.readTree(jsonFile))
            }
            mapOfData.put(folderName, listOfJsonNodes)
        }
    }

    fun isType(jsonNode: JsonNode,type: String): Boolean {
        return jsonNode.isMissingNode != true && jsonNode.get("jsonkey").textValue().equals(type)
    }
}