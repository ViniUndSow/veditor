package com.prasse.veditor.controller

import com.prasse.veditor.service.JsonLoader
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@Controller
class FragmentController(
    val jsonLoader: JsonLoader
) {

    @GetMapping("/loadAdditional/disciples/{count}")
    fun loadAdditional(model: Model, @PathVariable("count") count: Int): String {
        model.addAttribute("id", "discipline$count")
        model.addAttribute("disciplines", jsonLoader.loadDataWithDefault("DISCIPLINE"))
        model.addAttribute("name", "discipline$count")
        model.addAttribute("labelName","Diszipline ${count-3}")
        return "fragments/disciples"
    }

    @GetMapping("/saveToast")
    fun saveToast(model: Model): String {
        model.addAttribute("headline", "Daten erfolgreich gespeichert.")
        model.addAttribute("message", "Die eigentragenen Daten wurden erfolgreich gespeichert.")
        return "fragments/toast"
    }
}