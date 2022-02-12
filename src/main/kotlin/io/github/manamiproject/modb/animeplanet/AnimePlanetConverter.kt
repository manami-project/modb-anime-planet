package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.converter.AnimeConverter
import io.github.manamiproject.modb.core.extensions.EMPTY
import io.github.manamiproject.modb.core.models.*
import io.github.manamiproject.modb.core.models.Anime.Status.*
import io.github.manamiproject.modb.core.models.Anime.Status.UNKNOWN
import io.github.manamiproject.modb.core.models.Anime.Type
import io.github.manamiproject.modb.core.models.Anime.Type.*
import io.github.manamiproject.modb.core.models.AnimeSeason.Season.UNDEFINED
import io.github.manamiproject.modb.core.models.Duration.TimeUnit.MINUTES
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URI
import java.time.Clock
import java.time.LocalDate

/**
 * Converts raw data to an [Anime].
 * @since 1.0.0
 * @param config Configuration for converting data.
 */
public class AnimePlanetConverter(
    private val config: MetaDataProviderConfig = AnimePlanetConfig,
    private val clock: Clock = Clock.systemUTC(),
) : AnimeConverter {

    override fun convert(rawContent: String): Anime {

        val document = Jsoup.parse(rawContent)
        val thumbnail = extractThumbnail(document)

        return Anime(
            _title = extractTitle(document),
            episodes = extractEpisodes(document),
            type = extractType(document),
            picture = extractPicture(thumbnail),
            thumbnail = thumbnail,
            status = extractStatus(document),
            duration = extractDuration(document),
            animeSeason = extractAnimeSeason(document)
        ).apply {
            addSources(extractSourcesEntry(document))
            addSynonyms(extractSynonyms(document))
            addRelations(extractRelatedAnime(document))
            addTags(extractTags(document))
        }
    }

    private fun extractStatus(document: Document): Anime.Status {
        val value = document.select("section[class=pure-g entryBar]")
            .select("span[class=iconYear]")
            .text()
            .trim()

        val currentYear = LocalDate.now(clock).year

        // shows only one year
        if (REGEX_YEAR.matches(value)) {
            val year = value.toInt()
            return when {
                year > currentYear -> UPCOMING
                year < currentYear -> FINISHED
                else -> UNKNOWN
            }
        }

        // shows range of two years
        if (Regex("[0-9]{4} - [0-9]{4}").matches(value)) {
            val year = REGEX_YEAR.findAll(value).last().value.toInt()
            return when {
                year > currentYear -> UPCOMING
                year < currentYear -> FINISHED
                else -> UNKNOWN
            }
        }

        // shows range from year to unknown
        if (Regex("[0-9]{4} - \\?").matches(value)) {
            val year = REGEX_YEAR.find(value)!!.value.toInt()
            return when {
                year > currentYear -> UPCOMING
                year < currentYear -> ONGOING
                else -> UNKNOWN
            }
        }

        // shows only TBA
        if (value == "TBA") {
            return UPCOMING
        }

        throw IllegalStateException("Unknown case for status [$value]")
    }

    private fun extractTitle(document: Document): Title {
        var title = document.select("h1[itemprop=name]").text().trim()

        if (title.contains(TITLE_CONTAINING_AT_CHAR)) {
            val jsonld = document.select("script[type=application/ld+json]").html().toString()
            title = Regex("\"name\":\"(?<title>.*?)\"").find(jsonld)?.groups?.get("title")?.value ?: EMPTY
        }

        if (title.isBlank()) {
            title = document.select("meta[property=og:title]").attr("content")
        }

        return title
    }

    private fun extractEpisodes(document: Document): Episodes {
        return document.select("span[class=type]").text().let {
            Regex("\\d+").find(it)?.value?.toInt() ?: 0
        }
    }

    private fun extractType(document: Document): Type {
        val textValue = document.select("span[class=type]").text().substringBefore('(').lowercase().let {
            Regex("([a-z]| )+").find(it)?.value?.trim() ?: EMPTY
        }

        return when(textValue) {
            "dvd special" -> SPECIAL
            "movie" -> MOVIE
            "music video" -> SPECIAL
            "other" -> SPECIAL
            "ova" -> OVA
            "tv" -> TV
            "tv special" -> SPECIAL
            "web" -> ONA
            else -> throw IllegalStateException("Unknown type [$textValue]")
        }
    }

    private fun extractThumbnail(document: Document): URI {
        val textValue = document.select("img[itemprop=image]").attr("src")

        return if (textValue.isNotBlank()) {
            URI(textValue)
        } else {
            NO_PIC
        }
    }

    private fun extractPicture(thumbnail: URI): URI {
        return if (thumbnail != NO_PIC) {
            URI(thumbnail.toString().replace(Regex("-\\d+x\\d+"), EMPTY))
        } else {
            NO_PIC
        }
    }

    private fun extractDuration(document: Document): Duration {
        val durationInMinutes = document.select("span[class=type]").text().let {
            Regex("\\d+ min").find(it)?.value?.replace("min", EMPTY)?.trim()?.toInt() ?: 0
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
            season.size != 2 -> AnimeSeason(season = UNDEFINED, year = year)
            else -> AnimeSeason(season = AnimeSeason.Season.of(season[0]), year = season[1].toInt())
        }
    }

    private fun extractSourcesEntry(document: Document): List<URI> {
        val uri = document.select("link[rel=canonical]").attr("href").replace("www.", EMPTY)

        return listOf(URI(uri))
    }

    private fun extractSynonyms(document: Document): List<Title> {
        val heading = document.select("h2[class=aka]").text()

        val alternativeTitles = mutableListOf<String>()

        when {
            heading.startsWith(SINGLE_SYNONYM) -> alternativeTitles.add(heading.replace(SINGLE_SYNONYM, EMPTY).trim())
            heading.startsWith(MULTIPLE_SYNONYMS) -> heading.replace(MULTIPLE_SYNONYMS, EMPTY).
                split(',')
                .map { it.trim() }
                .forEach { alternativeTitles.add(it) }
        }

        if (alternativeTitles.any { it.contains(TITLE_CONTAINING_AT_CHAR) }) {
            alternativeTitles.removeIf { it.contains(TITLE_CONTAINING_AT_CHAR) }

            val jsonld = document.select("script[type=application/ld+json]").html().toString()
            alternativeTitles.add(
                    Regex("\"alternateName\":\\[\"(?<alternativeTitle>.*?)\"\\]").find(jsonld)
                    ?.groups
                    ?.get("alternativeTitle")
                    ?.value
                    ?: EMPTY
            )
        }

        return alternativeTitles
    }

    private fun extractTags(document: Document): List<Tag> {
        return document.select("div[class=tags]").select("a").map { it.text() }
    }

    private fun extractRelatedAnime(document: Document): List<URI> {
        return document.select("div#tabs--relations--anime > div")
            .select("a")
            .map { it.attr("href") }
            .map { it.replace("/anime/", EMPTY) }
            .map { config.buildAnimeLink(it) }
    }

    private companion object {
        private val REGEX_YEAR = Regex("[0-9]{4}")
        private val NO_PIC = URI("https://cdn.anime-planet.com/images/anime/default/default-anime-winter.png")
        private const val TITLE_CONTAINING_AT_CHAR= "[email protected]"
        private const val SINGLE_SYNONYM = "Alt title:"
        private const val MULTIPLE_SYNONYMS = "Alt titles:"
    }
}