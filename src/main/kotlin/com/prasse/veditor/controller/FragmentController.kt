package com.prasse.veditor.controller

import com.prasse.veditor.files.FolderService
import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.Clan
import com.prasse.veditor.model.Tugenden
import com.prasse.veditor.service.JsonLoader
import com.prasse.veditor.service.SaveService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import java.time.LocalDateTime

@Controller
class FragmentController(
    val jsonLoader: JsonLoader,
    val jsonService: JsonService,
    val saveService: SaveService,
    private val folderService: FolderService
) {

    private val logger = LoggerFactory.getLogger(FragmentController::class.java)

    @GetMapping("/loadAdditional/disciples/{count}")
    fun loadAdditional(model: Model, @PathVariable("count") count: Int): String {
        model.addAttribute("id", "discipline$count")
        model.addAttribute("disciplines", jsonLoader.loadDataWithDefault("DISCIPLINE"))
        model.addAttribute("name", "discipline$count")
        model.addAttribute("labelName","Diszipline ${count-3}")
        return "fragments/disciples"
    }

    @PostMapping("/toast")
    fun saveToast(
        model: Model,
        @RequestBody body: String): String {
        val bodyData = jsonService.jsonMapper.readTree(body)
        val erfolgreich = bodyData.get("success").asBoolean()
        if (erfolgreich) {
            model.addAttribute("headline", "Daten erfolgreich gespeichert.")
        } else {
            model.addAttribute("headline", "Fehler beim Speichern der Daten.")
        }
        model.addAttribute("message", bodyData.get("message").textValue())
        model.addAttribute("small", LocalDateTime.now().toString())
        model.addAttribute("id", bodyData.get("id").asText());
        return "fragments/toast"
    }


}