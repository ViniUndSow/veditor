package com.prasse.veditor.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class CharakterController(
    private val indexController: IndexController,
) {

    @GetMapping("/charakter")
    fun getCharakterDetail(model: Model): String{
        indexController.index(model)
        model.addAttribute("content", "charakter")
        return "index"
    }
}