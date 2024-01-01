package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.config.AnimeId
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.coroutines.ModbDispatchers.LIMITED_NETWORK
import io.github.manamiproject.modb.core.downloader.Downloader
import io.github.manamiproject.modb.core.extensions.EMPTY
import io.github.manamiproject.modb.core.httpclient.DefaultHttpClient
import io.github.manamiproject.modb.core.httpclient.HttpClient
import io.github.manamiproject.modb.core.logging.LoggerDelegate
import kotlinx.coroutines.withContext

/**
 * Downloads anime data from anime-planet.com
 * @since 1.0.0
 * @param config Configuration for downloading data.
 * @param httpClient To actually download the anime data.
 */
public class AnimePlanetDownloader(
    private val config: MetaDataProviderConfig,
    private val httpClient: HttpClient = DefaultHttpClient(isTestContext = config.isTestContext()),
) : Downloader {

    override suspend fun download(id: AnimeId, onDeadEntry: suspend (AnimeId) -> Unit): String = withContext(LIMITED_NETWORK) {
        log.debug { "Downloading [animePlanetId=$id]" }

        val response = httpClient.get(
            url = config.buildDataDownloadLink(id).toURL(),
            headers = mapOf("host" to listOf("www.${config.hostname()}")),
        )

        check(response.bodyAsText.isNotBlank()) { "Response body was blank for [animePlanetId=$id] with response code [${response.code}]" }

        if (response.bodyAsText.contains("You searched for") && response.bodyAsText.contains("...but we couldn't find anything.")) {
            onDeadEntry.invoke(id)
            return@withContext EMPTY
        }

        return@withContext when(response.code) {
            200 -> response.bodyAsText
            else -> throw IllegalStateException("Unable to determine the correct case for [animePlanetId=$id], [responseCode=${response.code}]")
        }
    }

    private companion object {
        private val log by LoggerDelegate()
    }
}