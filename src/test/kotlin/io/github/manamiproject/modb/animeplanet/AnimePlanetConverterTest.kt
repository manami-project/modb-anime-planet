package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.config.AnimeId
import io.github.manamiproject.modb.core.config.Hostname
import io.github.manamiproject.modb.core.config.MetaDataProviderConfig
import io.github.manamiproject.modb.core.models.Anime.Status.UNKNOWN
import io.github.manamiproject.modb.core.models.Anime.Type.*
import io.github.manamiproject.modb.core.models.AnimeSeason
import io.github.manamiproject.modb.core.models.AnimeSeason.Season.*
import io.github.manamiproject.modb.core.models.Duration
import io.github.manamiproject.modb.core.models.Duration.TimeUnit.HOURS
import io.github.manamiproject.modb.core.models.Duration.TimeUnit.MINUTES
import io.github.manamiproject.modb.test.loadTestResource
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.net.URI

internal class AnimePlanetConverterTest {

    @Nested
    inner class TitleTests {

        @Test
        fun `title containing special chars`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/title/special_chars.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.title).isEqualTo("PriPara Movie: Minna no Akogare♪ Let's Go☆Prix Paris")
        }

        @Test
        fun `extract title from jsonld if it was replaces by 'email protected'`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/title/title_replaced_by_email_protected.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.title).isEqualTo("Malice@Doll")
        }

        @Test
        fun `extract title from meta tag if it was replaces by 'email protected' and jsonld does not exist`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/title/title_replaced_by_email_protected_no_jsonld.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.title).isEqualTo("The iDOLM@STER Million Live!")
        }
    }

    @Nested
    inner class EpisodesTests {

        @Test
        fun `1 episode`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/episodes/1_ep.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.episodes).isOne()
        }

        @Test
        fun `135 episodes`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/episodes/135+_eps.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.episodes).isEqualTo(135)
        }

        @Test
        fun `25 episodes`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/episodes/25_eps.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.episodes).isEqualTo(25)
        }

        @Test
        fun `no episodes is mapped to 1 episode`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/episodes/no_episode.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.episodes).isZero()
        }
    }

    @Nested
    inner class TypeTests {

        @Test
        fun `'web' is mapped to ONA`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/web.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(ONA)
        }

        @Test
        fun `'TV Special' is mapped to Special`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/tv_special.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(Special)
        }

        @Test
        fun `'TV' is mapped to TV`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/tv.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(TV)
        }

        @Test
        fun `'OVA' is mapped to OVA`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/ova.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(OVA)
        }

        @Test
        fun `'other' is mapped to Special`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/other.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(Special)
        }

        @Test
        fun `'music video' is mapped to Special`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/music_video.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(Special)
        }

        @Test
        fun `'movie' is mapped to Movie`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/movie.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(Movie)
        }

        @Test
        fun `'dvd special' is mapped to Special`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/dvd_special.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(Special)
        }

        @Test
        fun `value without braces is extracted correctly`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/type/no_braces.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.type).isEqualTo(TV)
        }
    }

    @Nested
    inner class PictureAndThumbnailTests {

        @Test
        fun `correctly extract picture and thumbnail`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/picture_and_thumbnail/picture_and_thumbnail_available.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.picture.toString()).isEqualTo("https://anime-planet.com/images/anime/covers/natsumes-book-of-friends-season-6-specials-9230.jpg")
            assertThat(result.thumbnail.toString()).isEqualTo("https://anime-planet.com/images/anime/covers/thumbs/natsumes-book-of-friends-season-6-specials-9230.jpg")
        }

        @Test
        fun `neither picture nor thumbnail`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/picture_and_thumbnail/neither_picture_nor_thumbnail.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.picture.toString()).isEqualTo("https://anime-planet.com/inc/img/blank_main.jpg")
            assertThat(result.thumbnail.toString()).isEqualTo("https://anime-planet.com/inc/img/blank_main.jpg")
        }
    }

    @Nested
    inner class StatusTests {

        @Test
        fun `always returns UNKNOWN, because the info is missing on anime-planet`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/status/unknown.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.status).isEqualTo(UNKNOWN)
        }
    }

    @Nested
    inner class DurationTests {

        @Test
        fun `1 hour`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/duration/1_hour.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.duration).isEqualTo(Duration(1, HOURS))
        }

        @Test
        fun `1 min`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/duration/1_min.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.duration).isEqualTo(Duration(1, MINUTES))
        }

        @Test
        fun `2 hours`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/duration/2_hours.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.duration).isEqualTo(Duration(2, HOURS))
        }

        @Test
        fun `2 hours 15 minutes`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/duration/2_hours_15_min.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.duration).isEqualTo(Duration(135, MINUTES))
        }

        @Test
        fun `10 minutes`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/duration/10_min.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.duration).isEqualTo(Duration(10, MINUTES))
        }
    }

    @Nested
    inner class AnimeSeasonTests {

        @Test
        fun `season is undefined, but year is set`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/anime_season/year_only.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.animeSeason).isEqualTo(AnimeSeason(UNDEFINED, 2016))
        }

        @Test
        fun `season is summer`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/anime_season/summer.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.animeSeason).isEqualTo(AnimeSeason(SUMMER, 2020))
        }

        @Test
        fun `season is spring`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/anime_season/spring.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.animeSeason).isEqualTo(AnimeSeason(SPRING, 2020))
        }

        @Test
        fun `season is winter`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/anime_season/winter.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.animeSeason).isEqualTo(AnimeSeason(WINTER, 2020))
        }

        @Test
        fun `season is fall`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/anime_season/fall.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.animeSeason).isEqualTo(AnimeSeason(FALL, 2020))
        }

        @Test
        fun `tba - no year, no season`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/anime_season/tba.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.animeSeason).isEqualTo(AnimeSeason(UNDEFINED, 0))
        }
    }

    @Nested
    inner class SourcesTests {

        @Test
        fun `extract url slug as id`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/sources/id.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.sources).containsExactly(URI("https://anime-planet.com/anime/code-geass-lelouch-of-the-rebellion-r2"))
        }
    }

    @Nested
    inner class SynonymsTests {

        @Test
        fun `successfully extract the alternative title`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/synonyms/synonyms_available.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.synonyms).containsExactly("Kimi no Na wa.")
        }

        @Test
        fun `alternative title is not available`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/synonyms/synonyms_not_available.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.synonyms).isEmpty()
        }

        @Test
        fun `extract synonym from jsonld if it was replaces by 'email protected'`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/synonyms/synonyms_replaced_by_email_protected.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.synonyms).containsExactly("GJ Club@")
        }

        @Test
        fun `extract multiple synonyms`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/synonyms/multiple_synonyms.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.synonyms).containsExactly(
                    "\"Uchuu Senkan Yamato\" to Iu Jidai: Seireki 2202-nen no Sentaku"
            )
        }
    }

    @Nested
    inner class RelatedAnimeTests {

        @Test
        fun `extract same franchise and other franchise`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/related_anime/same_franchise_and_other_franchise.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.relatedAnime).containsExactly(
                URI("https://anime-planet.com/anime/fullmetal-alchemist"),
                URI("https://anime-planet.com/anime/fullmetal-alchemist-brotherhood-4-koma-theater"),
                URI("https://anime-planet.com/anime/fullmetal-alchemist-brotherhood-specials"),
                URI("https://anime-planet.com/anime/fullmetal-alchemist-premium-collection"),
                URI("https://anime-planet.com/anime/fullmetal-alchemist-reflections"),
                URI("https://anime-planet.com/anime/fullmetal-alchemist-the-movie-conqueror-of-shamballa"),
                URI("https://anime-planet.com/anime/fullmetal-alchemist-the-sacred-star-of-milos")
            )
        }

        @Test
        fun `extract related anime of there are only same franchise entries`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/related_anime/same_franchise_only.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.relatedAnime).containsExactly(
                URI("https://anime-planet.com/anime/demon-slayer-kimetsu-no-yaiba-movie-mugen-train")
            )
        }

        @Test
        fun `no related anime`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/related_anime/no_related_anime.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.relatedAnime).isEmpty()
        }
    }

    @Nested
    inner class TagsTests {

        @Test
        fun `successfully extract the tags`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/tags/various_tags.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.tags).containsExactly(
                "based on a manga",
                "dark fantasy",
                "fantasy",
                "horror",
                "isolated society",
                "mind games",
                "mystery",
                "noitamina",
                "orphans",
                "outside world",
                "psychological",
                "sci fi",
                "shounen",
                "thriller"
            )
        }

        @Test
        fun `tags not available`() {
            // given
            val testAnimePlanetConfig = object : MetaDataProviderConfig by MetaDataProviderTestConfig {
                override fun hostname(): Hostname = AnimePlanetConfig.hostname()
                override fun buildAnimeLink(id: AnimeId): URI = AnimePlanetConfig.buildAnimeLink(id)
            }

            val testFileContent = loadTestResource("file_converter_tests/tags/no_tags.html")

            val converter = AnimePlanetConverter(testAnimePlanetConfig)

            // when
            val result = converter.convert(testFileContent)

            // then
            assertThat(result.tags).isEmpty()
        }
    }
}