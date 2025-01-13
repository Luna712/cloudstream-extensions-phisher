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

import android.widget.Toast
import com.lagradost.cloudstream3.CommonActivity.showToast

import com.lagradost.cloudstream3.USER_AGENT
import com.lagradost.cloudstream3.utils.M3u8Helper
import com.lagradost.cloudstream3.utils.getAndUnpack
import com.lagradost.cloudstream3.utils.getPacked

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
    override val name = "AsianLoad"
    override val mainUrl = "https://asianload.org"
    override val requiresReferer = true

    override fun getExtractorUrl(id: String): String {
        showToast("id: $id", Toast.LENGTH_LONG)
        return "$mainUrl/view?id=$id"
    }

    override suspend fun getUrl(
        url: String,
        referer: String?,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ) {
        showToast("url: $url", Toast.LENGTH_LONG)
        val headers = mapOf(
            "Accept" to "*/*",
            "Connection" to "keep-alive",
            "Sec-Fetch-Dest" to "empty",
            "Sec-Fetch-Mode" to "cors",
            "Sec-Fetch-Site" to "cross-site",
            "Origin" to "$mainUrl/",
            "User-Agent" to USER_AGENT
        )
        val response = app.get(url, referer = referer)
        val script = if (!getPacked(response.text).isNullOrEmpty()) {
            getAndUnpack(response.text)
        } else if (!response.document.select("script").firstOrNull {
                it.html().contains("jwplayer(\"vplayer\").setup(")
            }?.html().isNullOrEmpty()
        ) {
            response.document.select("script").firstOrNull {
                it.html().contains("jwplayer(\"vplayer\").setup(")
            }?.html()
        } else {
            response.document.selectFirst("script:containsData(sources:)")?.data()
        }
        val m3u8 =
            Regex("file:\\s*\"(.*?m3u8.*?)\"").find(script ?: return)?.groupValues?.getOrNull(1)
        M3u8Helper.generateM3u8(
            name,
            m3u8 ?: return,
            mainUrl,
            headers = headers
        ).forEach(callback)
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
