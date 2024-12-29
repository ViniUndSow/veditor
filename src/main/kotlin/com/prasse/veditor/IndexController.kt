package com.prasse.veditor

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class IndexController(
    val clanLoaerService: ClanLoaderService
) {

    @GetMapping("/")
    fun index(model: Model): String {

        model.addAttribute("siteTitle", "Editor")
        model.addAttribute("clans", clanLoaerService.getClanData())
        return "index"
    }
}