package io.github.manamiproject.modb.animeplanet

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.URL

internal class AnimePlanetConfigTest {

    @Test
    fun `isTestContext is false`() {
        // when
        val result = AnimePlanetConfig.isTestContext()

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `hostname must be correct`() {
        // when
        val result = AnimePlanetConfig.hostname()

        // then
        assertThat(result).isEqualTo("anime-planet.com")
    }

    @Test
    fun `build anime link URL correctly`() {
        // given
        val id = "black-clover"

        // when
        val result = AnimePlanetConfig.buildAnimeLinkUrl(id)

        // then
        assertThat(result).isEqualTo(URL("https://anime-planet.com/anime/$id"))
    }

    @Test
    fun `build data download URL correctly`() {
        // given
        val id = "black-clover"

        // when
        val result = AnimePlanetConfig.buildDataDownloadUrl(id)

        // then
        assertThat(result).isEqualTo(URL("https://anime-planet.com/anime/$id"))
    }

    @Test
    fun `file suffix must be json`() {
        // when
        val result = AnimePlanetConfig.fileSuffix()

        // then
        assertThat(result).isEqualTo("html")
    }
}