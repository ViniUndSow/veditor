package com.prasse.veditor.controller

import com.prasse.veditor.files.JsonService
import com.prasse.veditor.service.SaveService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SaveController(
    private val jsonService: JsonService,
    private val saveService: SaveService
) {

    @PostMapping("/save")
    fun saveData(@RequestBody body: String): ResponseEntity<String> {
        try {
            saveService.saveChracterData(jsonService.jsonMapper.readTree(body))
            return ResponseEntity.ok("Speichern der Daten erfolgreich");
        } catch (ex: Exception) {
            return ResponseEntity.badRequest().body(ex.message);
        }
    }
}