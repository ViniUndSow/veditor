package com.prasse.veditor

import com.prasse.veditor.files.JsonService
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class IndexController(
    val clanLoaerService: ClanLoaderService,
    val talentService: TalentService,
    val desciplineService: DesciplineService,
    val jsonService: JsonService
) {

    @GetMapping("/")
    fun index(model: Model): String {

        model.addAttribute("siteTitle", "Editor")
        model.addAttribute("clans", clanLoaerService.loadClans())
        model.addAttribute("talente", talentService.loadTalents())
        model.addAttribute("disciplines", desciplineService.loadTalents())
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
}