package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.coroutines.CoroutineManager.runCoroutine
import io.github.manamiproject.modb.core.extensions.writeToFile
import io.github.manamiproject.modb.test.testResource
import java.nio.file.Path
import java.nio.file.Paths

internal fun main() {
    val downloader = AnimePlanetDownloader(AnimePlanetConfig)
    
    runCoroutine {
        downloader.download("sleepy-princess-in-the-demon-castle").writeToFile(resourceFile("file_converter_tests/anime_season/fall.html"))
        downloader.download("a-whisker-away").writeToFile(resourceFile("file_converter_tests/anime_season/spring.html"))
        downloader.download("japan-sinks-2020").writeToFile(resourceFile("file_converter_tests/anime_season/summer.html"))
        downloader.download("world-end-economica").writeToFile(resourceFile("file_converter_tests/anime_season/tba.html"))
        downloader.download("in-spectre").writeToFile(resourceFile("file_converter_tests/anime_season/winter.html"))
        downloader.download("your-name").writeToFile(resourceFile("file_converter_tests/anime_season/year_only.html"))
    
        downloader.download("sore-ike-anpanman-kyouryuu-nosshii-no-daibouken").writeToFile(resourceFile("file_converter_tests/duration/1_hour.html"))
        downloader.download("shigeru").writeToFile(resourceFile("file_converter_tests/duration/1_min.html"))
        downloader.download("a-letter-to-momo").writeToFile(resourceFile("file_converter_tests/duration/2_hours.html"))
        downloader.download("mobile-suit-gundam-ii-soldiers-of-sorrow").writeToFile(resourceFile("file_converter_tests/duration/2_hours_15_min.html"))
        downloader.download("black-jack-heian-sento").writeToFile(resourceFile("file_converter_tests/duration/10_min.html"))
    
        downloader.download("pripara-movie-minna-no-akogare-lets-go-prix-paris").writeToFile(resourceFile("file_converter_tests/episodes/1_ep.html"))
        downloader.download("code-geass-lelouch-of-the-rebellion-r2").writeToFile(resourceFile("file_converter_tests/episodes/25_eps.html"))
        downloader.download("detective-conan").writeToFile(resourceFile("file_converter_tests/episodes/1081+_eps.html"))
        downloader.download("a-girl-her-guard-dog").writeToFile(resourceFile("file_converter_tests/episodes/no_episodes.html"))
    
        downloader.download("ado-usseewa").writeToFile(resourceFile("file_converter_tests/picture_and_thumbnail/ensure_primary_screenshot_as_picture.html"))
        downloader.download("dog-signal").writeToFile(resourceFile("file_converter_tests/picture_and_thumbnail/neither_picture_nor_thumbnail.html"))
        downloader.download("natsumes-book-of-friends-season-6-specials").writeToFile(resourceFile("file_converter_tests/picture_and_thumbnail/picture_and_thumbnail_available.html"))
    
        downloader.download("mainichi-jk-kikaku").writeToFile(resourceFile("file_converter_tests/related_anime/no_related_anime.html"))
        downloader.download("fullmetal-alchemist-brotherhood").writeToFile(resourceFile("file_converter_tests/related_anime/same_franchise_and_other_franchise.html"))
        downloader.download("demon-slayer-kimetsu-no-yaiba").writeToFile(resourceFile("file_converter_tests/related_anime/same_franchise_only.html"))
    
        downloader.download("code-geass-lelouch-of-the-rebellion-r2").writeToFile(resourceFile("file_converter_tests/sources/id.html"))
    
        downloader.download("blade-of-the-immortal-2019").writeToFile(resourceFile("file_converter_tests/status/range_-_ends_in_the_past.html"))
        downloader.download("attack-on-titan-the-final-season").writeToFile(resourceFile("file_converter_tests/status/range_-_ends_same_year.html"))
        downloader.download("amemiya-san").writeToFile(resourceFile("file_converter_tests/status/range_to_unknown_-_starts_in_the_past.html"))
        downloader.download("kubo-wont-let-me-be-invisible").writeToFile(resourceFile("file_converter_tests/status/range_to_unknown_-_starts_same_year.html"))
        downloader.download("the-elder-sister-like-one").writeToFile(resourceFile("file_converter_tests/status/tba.html"))
        downloader.download("laid-back-camp-2nd-season").writeToFile(resourceFile("file_converter_tests/status/year_is_current_year.html"))
        downloader.download("solo-leveling").writeToFile(resourceFile("file_converter_tests/status/year_in_the_future.html"))
        downloader.download("banner-of-the-stars").writeToFile(resourceFile("file_converter_tests/status/year_in_the_past.html"))
    
        downloader.download("star-blazers-space-battleship-yamato-2202-movie").writeToFile(resourceFile("file_converter_tests/synonyms/multiple_synonyms.html"))
        downloader.download("your-name").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_available.html"))
        downloader.download("black-clover").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_not_available.html"))
        downloader.download("gj-bu-at").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_replaced_by_email_protected.html"))
    
        downloader.download("mainichi-jk-kikaku").writeToFile(resourceFile("file_converter_tests/tags/no_tags.html"))
        downloader.download("the-promised-neverland").writeToFile(resourceFile("file_converter_tests/tags/various_tags.html"))
    
        downloader.download("pripara-movie-minna-no-akogare-lets-go-prix-paris").writeToFile(resourceFile("file_converter_tests/title/special_chars.html"))
        downloader.download("malice-doll").writeToFile(resourceFile("file_converter_tests/title/title_replaced_by_email_protected.html"))
        downloader.download("the-idolmaster-million-live").writeToFile(resourceFile("file_converter_tests/title/title_replaced_by_email_protected_no_jsonld.html"))
    
        downloader.download("natsumes-book-of-friends-season-6-specials").writeToFile(resourceFile("file_converter_tests/type/dvd_special.html"))
        downloader.download("your-name").writeToFile(resourceFile("file_converter_tests/type/movie.html"))
        downloader.download("shelter").writeToFile(resourceFile("file_converter_tests/type/music_video.html"))
        downloader.download("dog-signal").writeToFile(resourceFile("file_converter_tests/type/no_braces.html"))
        downloader.download("gintama-jump-festa-2015").writeToFile(resourceFile("file_converter_tests/type/other.html"))
        downloader.download("rurouni-kenshin-tsuiokuhen").writeToFile(resourceFile("file_converter_tests/type/ova.html"))
        downloader.download("fullmetal-alchemist-brotherhood").writeToFile(resourceFile("file_converter_tests/type/tv.html"))
        downloader.download("hanamonogatari").writeToFile(resourceFile("file_converter_tests/type/tv_special.html"))
        downloader.download("planetarian").writeToFile(resourceFile("file_converter_tests/type/web.html"))

        println("Done")
    }
}

private fun resourceFile(file: String): Path {
    return Paths.get(
        testResource(file).toAbsolutePath()
            .toString()
            .replace("/build/resources/test/", "/src/test/resources/")
    )
}