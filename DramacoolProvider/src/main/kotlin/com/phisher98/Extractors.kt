package com.phisher98

import android.util.Log
import com.lagradost.cloudstream3.SubtitleFile
import com.lagradost.cloudstream3.app
import com.lagradost.cloudstream3.argamap
import com.lagradost.cloudstream3.extractors.MixDrop
import com.lagradost.cloudstream3.extractors.StreamWishExtractor
import com.lagradost.cloudstream3.extractors.VidhideExtractor
import com.lagradost.cloudstream3.extractors.helper.GogoHelper
import com.lagradost.cloudstream3.utils.ExtractorApi
import com.lagradost.cloudstream3.utils.ExtractorLink
import com.lagradost.cloudstream3.utils.httpsify
import com.lagradost.cloudstream3.utils.loadExtractor
import com.lagradost.cloudstream3.utils.M3u8Helper
import com.lagradost.cloudstream3.utils.getQualityFromName
import java.net.URI

class dlions : VidhideExtractor() {
    override var name = "Dlions"
    override var mainUrl = "https://dlions.pro"
}

class MixDropSi : MixDrop() {
    override var mainUrl = "https://mixdrop.si"
}

class DramacoolExtractor : StreamWishExtractor() {
    override var name = "Dramacool"
    override var mainUrl = "https://dramacool.men"
}

class Vkspeed : ExtractorApi() {
    override var name = "VKspeed"
    override var mainUrl = "https://asianload.org"

    private val sourceRegex = Regex("""sources:[\W\w]*?file:\s*?["'](.*?)["']""")
    override suspend fun getUrl(url: String, referer: String?): List<ExtractorLink> {
        val extractedLinksList: MutableList<ExtractorLink> = mutableListOf()
        with(app.get(url, referer = referer)) {
            sourceRegex.findAll(this.text).forEach { sourceMatch ->
                val extractedUrl = sourceMatch.groupValues[1]
                // Trusting this isn't mp4, may fuck up stuff
                if (URI(extractedUrl).path.endsWith(".m3u8")) {
                    M3u8Helper.generateM3u8(
                        name,
                        extractedUrl,
                        url,
                        headers = mapOf("referer" to this.url)
                    ).forEach { link ->
                        extractedLinksList.add(link)
                    }
                } else if (extractedUrl.endsWith(".mp4")) {
                    extractedLinksList.add(
                        ExtractorLink(
                            name,
                            name,
                            extractedUrl,
                            url.replace(" ", "%20"),
                            getQualityFromName(sourceMatch.groupValues[2]),
                        )
                    )
                }
            }
            return extractedLinksList
        }
    }
}

class dhtpre : StreamWishExtractor() {
    override var name = "EarnVids"
    override var mainUrl = "https://dhtpre.com"
}

class nikaplayerr : StreamWishExtractor() {
    override var name = "EarnVids"
    override var mainUrl = "https://nikaplayerr.com"
}

class peytonepre : StreamWishExtractor() {
    override var name = "EarnVids"
    override var mainUrl = "https://peytonepre.com"
}
