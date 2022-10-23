package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.extensions.writeToFile
import io.github.manamiproject.modb.test.testResource
import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val downloader = AnimePlanetDownloader(AnimePlanetConfig)
    
    runBlocking { 
        downloader.downloadSuspendable("sleepy-princess-in-the-demon-castle").writeToFile(resourceFile("file_converter_tests/anime_season/fall.html"))
        downloader.downloadSuspendable("a-whisker-away").writeToFile(resourceFile("file_converter_tests/anime_season/spring.html"))
        downloader.downloadSuspendable("japan-sinks-2020").writeToFile(resourceFile("file_converter_tests/anime_season/summer.html"))
        downloader.downloadSuspendable("world-end-economica").writeToFile(resourceFile("file_converter_tests/anime_season/tba.html"))
        downloader.downloadSuspendable("in-spectre").writeToFile(resourceFile("file_converter_tests/anime_season/winter.html"))
        downloader.downloadSuspendable("your-name").writeToFile(resourceFile("file_converter_tests/anime_season/year_only.html"))
    
        downloader.downloadSuspendable("sore-ike-anpanman-kyouryuu-nosshii-no-daibouken").writeToFile(resourceFile("file_converter_tests/duration/1_hour.html"))
        downloader.downloadSuspendable("shigeru").writeToFile(resourceFile("file_converter_tests/duration/1_min.html"))
        downloader.downloadSuspendable("a-letter-to-momo").writeToFile(resourceFile("file_converter_tests/duration/2_hours.html"))
        downloader.downloadSuspendable("mobile-suit-gundam-ii-soldiers-of-sorrow").writeToFile(resourceFile("file_converter_tests/duration/2_hours_15_min.html"))
        downloader.downloadSuspendable("black-jack-heian-sento").writeToFile(resourceFile("file_converter_tests/duration/10_min.html"))
    
        downloader.downloadSuspendable("pripara-movie-minna-no-akogare-lets-go-prix-paris").writeToFile(resourceFile("file_converter_tests/episodes/1_ep.html"))
        downloader.downloadSuspendable("code-geass-lelouch-of-the-rebellion-r2").writeToFile(resourceFile("file_converter_tests/episodes/25_eps.html"))
        downloader.downloadSuspendable("detective-conan").writeToFile(resourceFile("file_converter_tests/episodes/1010+_eps.html"))
        downloader.downloadSuspendable("akebi-chan-no-sailor-fuku").writeToFile(resourceFile("file_converter_tests/episodes/no_episodes.html"))
    
        downloader.downloadSuspendable("kuma-kuma-kuma-bear-2").writeToFile(resourceFile("file_converter_tests/picture_and_thumbnail/neither_picture_nor_thumbnail.html"))
        downloader.downloadSuspendable("natsumes-book-of-friends-season-6-specials").writeToFile(resourceFile("file_converter_tests/picture_and_thumbnail/picture_and_thumbnail_available.html"))
    
        downloader.downloadSuspendable("mainichi-jk-kikaku").writeToFile(resourceFile("file_converter_tests/related_anime/no_related_anime.html"))
        downloader.downloadSuspendable("fullmetal-alchemist-brotherhood").writeToFile(resourceFile("file_converter_tests/related_anime/same_franchise_and_other_franchise.html"))
        downloader.downloadSuspendable("demon-slayer-kimetsu-no-yaiba").writeToFile(resourceFile("file_converter_tests/related_anime/same_franchise_only.html"))
    
        downloader.downloadSuspendable("code-geass-lelouch-of-the-rebellion-r2").writeToFile(resourceFile("file_converter_tests/sources/id.html"))
    
        downloader.downloadSuspendable("blade-of-the-immortal-2019").writeToFile(resourceFile("file_converter_tests/status/range_-_ends_in_the_past.html"))
        downloader.downloadSuspendable("attack-on-titan-the-final-season").writeToFile(resourceFile("file_converter_tests/status/range_-_ends_same_year.html"))
        downloader.downloadSuspendable("new-employee-hayato").writeToFile(resourceFile("file_converter_tests/status/range_to_unknown_-_starts_in_the_past.html"))
        downloader.downloadSuspendable("amemiya-san").writeToFile(resourceFile("file_converter_tests/status/range_to_unknown_-_starts_same_year.html"))
        downloader.downloadSuspendable("the-elder-sister-like-one").writeToFile(resourceFile("file_converter_tests/status/tba.html"))
        downloader.downloadSuspendable("laid-back-camp-2nd-season").writeToFile(resourceFile("file_converter_tests/status/year_is_current_year.html"))
        downloader.downloadSuspendable("attack-on-titan-the-final-season-part-ii").writeToFile(resourceFile("file_converter_tests/status/year_in_the_future.html"))
        downloader.downloadSuspendable("banner-of-the-stars").writeToFile(resourceFile("file_converter_tests/status/year_in_the_past.html"))
    
        downloader.downloadSuspendable("star-blazers-space-battleship-yamato-2202-movie").writeToFile(resourceFile("file_converter_tests/synonyms/multiple_synonyms.html"))
        downloader.downloadSuspendable("your-name").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_available.html"))
        downloader.downloadSuspendable("black-clover").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_not_available.html"))
        downloader.downloadSuspendable("gj-bu-at").writeToFile(resourceFile("file_converter_tests/synonyms/synonyms_replaced_by_email_protected.html"))
    
        downloader.downloadSuspendable("mainichi-jk-kikaku").writeToFile(resourceFile("file_converter_tests/tags/no_tags.html"))
        downloader.downloadSuspendable("the-promised-neverland").writeToFile(resourceFile("file_converter_tests/tags/various_tags.html"))
    
        downloader.downloadSuspendable("pripara-movie-minna-no-akogare-lets-go-prix-paris").writeToFile(resourceFile("file_converter_tests/title/special_chars.html"))
        downloader.downloadSuspendable("malice-doll").writeToFile(resourceFile("file_converter_tests/title/title_replaced_by_email_protected.html"))
        downloader.downloadSuspendable("the-idolmaster-million-live").writeToFile(resourceFile("file_converter_tests/title/title_replaced_by_email_protected_no_jsonld.html"))
    
        downloader.downloadSuspendable("natsumes-book-of-friends-season-6-specials").writeToFile(resourceFile("file_converter_tests/type/dvd_special.html"))
        downloader.downloadSuspendable("your-name").writeToFile(resourceFile("file_converter_tests/type/movie.html"))
        downloader.downloadSuspendable("shelter").writeToFile(resourceFile("file_converter_tests/type/music_video.html"))
        downloader.downloadSuspendable("soredemo-ayumu-wa-yosetekuru").writeToFile(resourceFile("file_converter_tests/type/no_braces.html"))
        downloader.downloadSuspendable("gintama-jump-festa-2015").writeToFile(resourceFile("file_converter_tests/type/other.html"))
        downloader.downloadSuspendable("rurouni-kenshin-tsuiokuhen").writeToFile(resourceFile("file_converter_tests/type/ova.html"))
        downloader.downloadSuspendable("fullmetal-alchemist-brotherhood").writeToFile(resourceFile("file_converter_tests/type/tv.html"))
        downloader.downloadSuspendable("hanamonogatari").writeToFile(resourceFile("file_converter_tests/type/tv_special.html"))
        downloader.downloadSuspendable("planetarian").writeToFile(resourceFile("file_converter_tests/type/web.html"))
    }
}

private fun resourceFile(file: String): Path {
    return Paths.get(
        testResource(file).toAbsolutePath()
            .toString()
            .replace("/build/resources/test/", "/src/test/resources/")
    )
}