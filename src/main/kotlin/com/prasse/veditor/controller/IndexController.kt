package com.prasse.veditor.controller

import com.prasse.veditor.files.JsonService
import com.prasse.veditor.model.Clan
import com.prasse.veditor.model.Tugenden
import com.prasse.veditor.service.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class IndexController(
    val jsonService: JsonService,
    val jsonLoader: JsonLoader
) {

    @GetMapping("/")
    fun index(model: Model): String {
        //Attribute
        model.addAttribute("spiritual", jsonLoader.loadDataWithDefault("SPIRITUAL"))
        model.addAttribute("socials", jsonLoader.loadDataWithDefault("SOCIAL"))
        model.addAttribute("physical", jsonLoader.loadDataWithDefault("PHYSICAL"))
        model.addAttribute("siteTitle", "Editor")
        model.addAttribute("clans", jsonLoader.loadData<Clan>(JsonLoader.CLAN))
        model.addAttribute("disciplines", jsonLoader.loadDataWithDefault("DISCIPLINE"))
        model.addAttribute("tugenden", jsonLoader.loadData<Tugenden>(JsonLoader.TUGENDEN))
        model.addAttribute("maxDisciplines", 8)
        model.addAttribute("background", jsonLoader.loadDataWithDefault(JsonLoader.BACKGROUND))
        // Fähigkeiten
        model.addAttribute("sect", jsonLoader.loadDataWithDefault(JsonLoader.SEKTE))
        model.addAttribute("knowledge", jsonLoader.loadDataWithDefault("KNOWLEDGE"))
        model.addAttribute("talente", jsonLoader.loadDataWithDefault(JsonLoader.TALENTE))
        model.addAttribute("skills", jsonLoader.loadDataWithDefault(JsonLoader.FERTIGKEITEN))
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

    @GetMapping("/loadAdditional/disciples/{count}")
    fun loadAdditional(model: Model, @PathVariable("count") count: Int): String {
        model.addAttribute("id", "discipline$count")
        model.addAttribute("disciplines", jsonLoader.loadDataWithDefault("DISCIPLINE"))
        model.addAttribute("name", "discipline$count")
        model.addAttribute("labelName","Diszipline ${count-3}")
        return "fragments/disciples"
    }
}