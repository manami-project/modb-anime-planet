package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.config.AnimeId
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.downloader.Downloader
import io.github.manamiproject.modb.core.httpclient.DefaultHttpClient
import io.github.manamiproject.modb.core.httpclient.HttpClient
import io.github.manamiproject.modb.core.httpclient.retry.RetryBehavior
import io.github.manamiproject.modb.core.httpclient.retry.RetryableRegistry
import io.github.manamiproject.modb.core.logging.LoggerDelegate
import io.github.manamiproject.modb.core.random

/**
 * Downloads anime data from anime-planet.com
 * @since 1.0.0
 * @param config Configuration for downloading data.
 * @param httpClient To actually download the anime data.
 */
public class AnimePlanetDownloader(
    private val config: MetaDataProviderConfig,
    private val httpClient: HttpClient = DefaultHttpClient()
) : Downloader {

    init {
        registerRetryBehavior()
    }

    override fun download(id: AnimeId, onDeadEntry: (AnimeId) -> Unit): String {
        log.debug("Downloading [animePlanetId={}]", id)

        val response = httpClient.get(
            url = config.buildDataDownloadLink(id).toURL(),
            headers = mapOf("host" to listOf("www.${config.hostname()}")),
            retryWith = config.hostname(),
        )

        check(response.body.isNotBlank()) { "Response body was blank for [animePlanetId=$id] with response code [${response.code}]" }

        return when(response.code) {
            200 -> response.body
            else -> throw IllegalStateException("Unable to determine the correct case for [animePlanetId=$id], [responseCode=${response.code}]")
        }
    }

    private fun registerRetryBehavior() {
        val retryBehaviorConfig = RetryBehavior(
                waitDuration = { random(4000, 8000) },
                retryOnResponsePredicate = { httpResponse -> listOf(500, 502, 521).contains(httpResponse.code) }
        )

        RetryableRegistry.register(config.hostname(), retryBehaviorConfig)
    }

    private companion object {
        private val log by LoggerDelegate()
    }
}