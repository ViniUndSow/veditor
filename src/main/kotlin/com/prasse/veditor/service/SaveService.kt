package com.prasse.veditor.service

import com.fasterxml.jackson.databind.JsonNode
import com.prasse.veditor.files.FolderService
import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.CharacterPreview
import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class SaveService(
    val folderService: FolderService,
    private val jsonService: JsonService
) {

    fun saveChracterData(jsonNode: JsonNode) {
        val jsonData = jsonNode.toPrettyString()
        val characterName = jsonNode.get("chracterName").asText()
        if(characterName.isNullOrBlank()){
            throw IllegalArgumentException("chracterName cannot be null or blank");
        }
        val file = Path.of(FolderService.CHARACTERS).resolve(characterName.plus(".json")).toFile()
        file.createNewFile()
        file.writeText(jsonNode.toPrettyString())
    }

    fun loadChracters(): List<CharacterPreview> {
        val characterList = mutableListOf<CharacterPreview>()
        Path.of(FolderService.CHARACTERS).toFile().listFiles()?.forEach { file ->
            characterList.add( CharacterPreview(name = file.nameWithoutExtension, 1, "Test"))
        }
        return characterList
    }

    fun loadSingleCharacter(characterName: String): JsonNode? {
        val data = Path.of(FolderService.CHARACTERS).resolve(characterName).toFile()
        if(data.exists()){
            return jsonService.jsonMapper.readTree(data)
        } else {
            return null
        }
    }
}