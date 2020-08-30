package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.converter.AnimeConverter
import io.github.manamiproject.modb.core.models.Anime
import io.github.manamiproject.modb.core.models.Anime.Status.UNKNOWN
import io.github.manamiproject.modb.core.models.Anime.Type
import io.github.manamiproject.modb.core.models.Anime.Type.*
import io.github.manamiproject.modb.core.models.AnimeSeason
import io.github.manamiproject.modb.core.models.AnimeSeason.Season.UNDEFINED
import io.github.manamiproject.modb.core.models.Duration
import io.github.manamiproject.modb.core.models.Duration.TimeUnit.MINUTES
import io.github.manamiproject.modb.core.models.Episodes
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URL

/**
 * Converts raw data to an [Anime].
 * @since 1.0.0
 * @param config Configuration for converting data.
 */
class AnimePlanetConverter(private val config: MetaDataProviderConfig = AnimePlanetConfig) : AnimeConverter {

    override fun convert(rawContent: String): Anime {

        val document = Jsoup.parse(rawContent)
        val picture = extractPicture(document)

        return Anime(
            _title = extractTitle(document),
            episodes = extractEpisodes(document),
            type = extractType(document),
            picture = picture,
            thumbnail = extractThumbnail(picture),
            status = UNKNOWN,
            duration = extractDuration(document),
            animeSeason = extractAnimeSeason(document)
        ).apply {
            addSources(extractSourcesEntry(document))
            addSynonyms(extractSynonyms(document))
            addRelations(extractRelatedAnime(document))
            addTags(extractTags(document))
        }
    }

    private fun extractTitle(document: Document): String = document.select("h1[itemprop=name]").text().trim()

    private fun extractEpisodes(document: Document): Episodes {
        return document.select("span[class=type]").text().let {
            Regex("\\d+").find(it)?.value?.toInt() ?: 0
        }
    }

    private fun extractType(document: Document): Type {
        val textValue = document.select("span[class=type]").text().substringBefore('(').toLowerCase().let {
            Regex("([a-z]| )+").find(it)?.value?.trim() ?: ""
        }

        return when(textValue) {
            "dvd special" -> Special
            "movie" -> Movie
            "music video" -> Special
            "other" -> Special
            "ova" -> OVA
            "tv" -> TV
            "tv special" -> Special
            "web" -> ONA
            else -> throw IllegalStateException("Unknown type [$textValue]")
        }
    }

    private fun extractPicture(document: Document): URL {
        val textValue = document.select("img[itemprop=image]").attr("src").let {
            Regex(".*?\\.jpg").find(it)?.value ?: ""
        }

        return if (textValue.isNotBlank()) {
            if (textValue.startsWith('/')) {
                URL("https://${config.hostname()}$textValue")
            } else {
                URL("https://${config.hostname()}/$textValue")
            }
        } else {
            NO_PIC
        }
    }

    private fun extractThumbnail(url: URL): URL {
        return if (url == NO_PIC) {
            NO_PIC
        } else {
            URL(url.toString().replace("/covers", "/covers/thumbs"))
        }
    }

    private fun extractDuration(document: Document): Duration {
        val durationInMinutes = document.select("span[class=type]").text().let {
            Regex("\\d+ min").find(it)?.value?.replace("min", "")?.trim()?.toInt() ?: 0
        }

        return Duration(durationInMinutes, MINUTES)
    }

    private fun extractAnimeSeason(document: Document): AnimeSeason {
        val yearNode = document.select("span[class=iconYear]")
        val year = yearNode.text().trim().let {
            it.toIntOrNull() ?: 0
        }

        val season = yearNode.next().next().text().trim().split(' ')

        return when {
            season.size != 2 -> AnimeSeason(season = UNDEFINED, _year = year)
            season.size == 2 -> AnimeSeason(season = AnimeSeason.Season.of(season[0]), _year = season[1].toInt())
            else -> AnimeSeason()
        }
    }

    private fun extractSourcesEntry(document: Document): List<URL> {
        val id = document.select("section[class=sidebarStats]").attr("data-url-slug")

        return listOf(config.buildAnimeLinkUrl(id))
    }

    private fun extractSynonyms(document: Document): List<String> {
        val alternativeTitle = document.select("h2[class=aka]").text().replace("Alt title:", "").trim()

        return listOf(alternativeTitle)
    }

    private fun extractTags(document: Document): List<String> {
        return document.select("div[class=tags]").select("a").map { it.text() }
    }

    private fun extractRelatedAnime(document: Document): List<URL> {
        return document.select("div#tabs--relations--anime > div")
            .select("a")
            .map { it.attr("href") }
            .map { it.replace("/anime/", "") }
            .map { config.buildAnimeLinkUrl(it) }
    }

    companion object {
        private val NO_PIC = URL("https://anime-planet.com/inc/img/blank_main.jpg")
    }
}