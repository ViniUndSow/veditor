package com.prasse.veditor

import com.prasse.veditor.files.JsonService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class IndexController(
    val clanLoaerService: ClanLoaderService,
    val talentService: TalentService,
    val desciplineService: DesciplineService,
    val jsonService: JsonService,
    val fertigkeitenService: FertigkeitenService
) {

    @GetMapping("/")
    fun index(model: Model): String {

        model.addAttribute("siteTitle", "Editor")
        model.addAttribute("clans", clanLoaerService.loadClans())
        model.addAttribute("disciplines", desciplineService.loadTalents())
        model.addAttribute("maxDisciplines", 8)
        // Attributes
        model.addAttribute("talente", talentService.loadTalents())
        model.addAttribute("skills", fertigkeitenService.loadSkills())
        return "index"
    }

    @PostMapping("/")
    fun reload(model: Model): String {
        jsonService.loadAllJsonFiles()
        talentService.loadTalents()
        clanLoaerService.loadClans()
        desciplineService.loadTalents()

        return "redirect:/"
    }

    @GetMapping("/loadAdditional/disciples/{count}")
    fun loadAdditional(model: Model, @PathVariable("count") count: Int): String {
        model.addAttribute("id", "discipline$count")
        model.addAttribute("disciplines", desciplineService.loadTalents())
        model.addAttribute("name", "discipline$count")
        model.addAttribute("labelName","Diszipline ${count-3}")
        return "fragments/disciples"
    }
}