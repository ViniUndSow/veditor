package com.prasse.veditor.service

import com.fasterxml.jackson.databind.JsonNode
import com.prasse.veditor.files.FolderService
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createFile

@Service
class SaveService(
    val folderService: FolderService
) {

    fun saveChracterData(jsonNode: JsonNode) {
        val jsonData = jsonNode.toPrettyString()
        val characterName = jsonNode.get("chracterName").asText()
        if(characterName.isNullOrBlank()){
            throw IllegalArgumentException("chracterName cannot be null or blank");
        }
        val file = Path.of(FolderService.CHARACTERS).resolve(characterName.plus(".json")).toFile()
        file.createNewFile()
        file.writeText(jsonData)
    }
}