package com.prasse.veditor.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SaveController {

    @PostMapping("/save")
    fun saveData(@RequestBody body: String): ResponseEntity<String> {
        println(body)
        return ResponseEntity.ok("Speichern der Daten erfolgreich");
    }
}