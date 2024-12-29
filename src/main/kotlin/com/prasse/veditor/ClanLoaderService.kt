package com.prasse.veditor

import org.springframework.stereotype.Service
import java.security.KeyStore.Entry

@Service
class ClanLoaderService {

    fun getClanData(): Map<String, String> {

        return mapOf("WHITE_VAMP" to "Weiße Vampiere",
                     "DARK_VAMP" to "Dunkle Vampiere")
    }
}