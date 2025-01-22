package com.prasse.veditor.files

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import java.io.File

/**
 * Dieser Service erzeugt die benötigten Ordner so wie die dazugehörigen "Custom Ordner"
 */
@Component
class FolderService {

    companion object {
        val CUSTOM = "data/custom"
        val CHARACTERS = "data/characters"
    }
    val listOfFolder = listOf("data", CUSTOM, "data/basic", CHARACTERS)

    @PostConstruct
    fun createDataFolder() {
        listOfFolder.forEach {
            val dataFolder = File(it)
            if (!dataFolder.exists()) {
                dataFolder.mkdirs()
            }
        }
    }

}