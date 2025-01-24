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
import org.springframework.web.bind.annotation.RequestParam

@Controller
class CharakterController(
    val jsonLoader: JsonLoader,
    val jsonService: JsonService,
    val saveService: SaveService,
    private val folderService: FolderService
) {

    private val logger = LoggerFactory.getLogger(CharakterController::class.java)

    @GetMapping("/character")
    fun index(model: Model,
              @RequestParam(name = "character", required = false) character: String): String {
        //Regelwerk
        model.addAttribute("regelwerke", folderService.getRegelwerke())
        model.addAttribute("character", character)

        if(character != null && character != "") {
            val char = saveService.loadSingleCharacter(character)!!
            char.fields().forEachRemaining {
                if(it.value.isInt) {
                    model.addAttribute(it.key, it.value.intValue())
                } else if (it.value.isTextual){
                    model.addAttribute(it.key, it.value.textValue())
                }  else {
                    logger.info("Keine Übereinstummung gefunden. [Key=${it.key}, Value=${it.value}]")
                }
            }
            model.addAttribute("mode", "VIEW")
        } else {
            model.addAttribute("mode", "EDIT")
        }
        return "characterSheet"
    }

    @GetMapping("/load/dataByRegelwerk")
    fun loadDataByRegelwerk(
        model: Model,
        @RequestParam(name = "regelwerk", required = true) regelwerk: String,
    ): String {
        //Attribute
        model.addAttribute("spiritual", jsonLoader.loadDataWithDefault("SPIRITUAL").get(regelwerk))
        model.addAttribute("socials", jsonLoader.loadDataWithDefault("SOCIAL").get(regelwerk))
        model.addAttribute("physical", jsonLoader.loadDataWithDefault("PHYSICAL").get(regelwerk))
        model.addAttribute("siteTitle", "Editor")
        model.addAttribute("clans", jsonLoader.loadData<Clan>(JsonLoader.CLAN).get(regelwerk))
        model.addAttribute("disciplines", jsonLoader.loadDataWithDefault("DISCIPLINE").get(regelwerk))
        model.addAttribute("tugenden", jsonLoader.loadData<Tugenden>(JsonLoader.TUGENDEN).get(regelwerk))
        model.addAttribute("maxDisciplines", 8)
        model.addAttribute("backgrounds", jsonLoader.loadDataWithDefault(JsonLoader.BACKGROUND).get(regelwerk))
        // Fähigkeiten
        model.addAttribute("sect", jsonLoader.loadDataWithDefault(JsonLoader.SEKTE).get(regelwerk))
        model.addAttribute("knowledge", jsonLoader.loadDataWithDefault("KNOWLEDGE").get(regelwerk))
        model.addAttribute("talente", jsonLoader.loadDataWithDefault(JsonLoader.TALENTE).get(regelwerk))
        model.addAttribute("skills", jsonLoader.loadDataWithDefault(JsonLoader.FERTIGKEITEN).get(regelwerk))

        return "stats"
    }
}