package com.prasse.veditor.controller

import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.Clan
import com.prasse.veditor.model.Tugenden
import com.prasse.veditor.service.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class IndexController(
    val jsonService: JsonService,
    val jsonLoader: JsonLoader,
    val saveService: SaveService
) {

    @GetMapping("/")
    fun index(model: Model): String {
        //Attribute
        model.addAttribute("content", "charakterauswahl")
        model.addAttribute("charaktere", saveService.loadChracters())

        return "index"
    }

    @PostMapping("/")
    fun reload(model: Model): String {
        jsonService.loadAllJsonFiles()
        jsonLoader.loadDataWithDefault("SPIRITUAL")
        jsonLoader.loadDataWithDefault("SOCIAL")
        jsonLoader.loadDataWithDefault("PHYSICAL")
        jsonLoader.loadData<Clan>(JsonLoader.CLAN)
        jsonLoader.loadData<Tugenden>(JsonLoader.TUGENDEN)
        jsonLoader.loadDataWithDefault("DISCIPLINE")
        // Fähigkeiten

        jsonLoader.loadDataWithDefault("KNOWLEDGE")
        jsonLoader.loadDataWithDefault("TALENTS")
        jsonLoader.loadDataWithDefault("SKILLS")

        return "redirect:/"
    }




}