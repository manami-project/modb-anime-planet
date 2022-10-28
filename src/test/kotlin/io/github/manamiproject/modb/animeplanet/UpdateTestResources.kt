package io.github.manamiproject.modb.animeplanet

import io.github.manamiproject.modb.core.extensions.writeToFileSuspendable
import io.github.manamiproject.modb.test.testResource
import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import java.nio.file.Paths

fun main() {
    val downloader = AnimePlanetDownloader(AnimePlanetConfig)
    
    runBlocking { 
        downloader.downloadSuspendable("sleepy-princess-in-the-demon-castle").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/fall.html"))
        downloader.downloadSuspendable("a-whisker-away").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/spring.html"))
        downloader.downloadSuspendable("japan-sinks-2020").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/summer.html"))
        downloader.downloadSuspendable("world-end-economica").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/tba.html"))
        downloader.downloadSuspendable("in-spectre").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/winter.html"))
        downloader.downloadSuspendable("your-name").writeToFileSuspendable(resourceFile("file_converter_tests/anime_season/year_only.html"))
    
        downloader.downloadSuspendable("sore-ike-anpanman-kyouryuu-nosshii-no-daibouken").writeToFileSuspendable(resourceFile("file_converter_tests/duration/1_hour.html"))
        downloader.downloadSuspendable("shigeru").writeToFileSuspendable(resourceFile("file_converter_tests/duration/1_min.html"))
        downloader.downloadSuspendable("a-letter-to-momo").writeToFileSuspendable(resourceFile("file_converter_tests/duration/2_hours.html"))
        downloader.downloadSuspendable("mobile-suit-gundam-ii-soldiers-of-sorrow").writeToFileSuspendable(resourceFile("file_converter_tests/duration/2_hours_15_min.html"))
        downloader.downloadSuspendable("black-jack-heian-sento").writeToFileSuspendable(resourceFile("file_converter_tests/duration/10_min.html"))
    
        downloader.downloadSuspendable("pripara-movie-minna-no-akogare-lets-go-prix-paris").writeToFileSuspendable(resourceFile("file_converter_tests/episodes/1_ep.html"))
        downloader.downloadSuspendable("code-geass-lelouch-of-the-rebellion-r2").writeToFileSuspendable(resourceFile("file_converter_tests/episodes/25_eps.html"))
        downloader.downloadSuspendable("detective-conan").writeToFileSuspendable(resourceFile("file_converter_tests/episodes/1010+_eps.html"))
        downloader.downloadSuspendable("akebi-chan-no-sailor-fuku").writeToFileSuspendable(resourceFile("file_converter_tests/episodes/no_episodes.html"))
    
        downloader.downloadSuspendable("kuma-kuma-kuma-bear-2").writeToFileSuspendable(resourceFile("file_converter_tests/picture_and_thumbnail/neither_picture_nor_thumbnail.html"))
        downloader.downloadSuspendable("natsumes-book-of-friends-season-6-specials").writeToFileSuspendable(resourceFile("file_converter_tests/picture_and_thumbnail/picture_and_thumbnail_available.html"))
    
        downloader.downloadSuspendable("mainichi-jk-kikaku").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/no_related_anime.html"))
        downloader.downloadSuspendable("fullmetal-alchemist-brotherhood").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/same_franchise_and_other_franchise.html"))
        downloader.downloadSuspendable("demon-slayer-kimetsu-no-yaiba").writeToFileSuspendable(resourceFile("file_converter_tests/related_anime/same_franchise_only.html"))
    
        downloader.downloadSuspendable("code-geass-lelouch-of-the-rebellion-r2").writeToFileSuspendable(resourceFile("file_converter_tests/sources/id.html"))
    
        downloader.downloadSuspendable("blade-of-the-immortal-2019").writeToFileSuspendable(resourceFile("file_converter_tests/status/range_-_ends_in_the_past.html"))
        downloader.downloadSuspendable("attack-on-titan-the-final-season").writeToFileSuspendable(resourceFile("file_converter_tests/status/range_-_ends_same_year.html"))
        downloader.downloadSuspendable("new-employee-hayato").writeToFileSuspendable(resourceFile("file_converter_tests/status/range_to_unknown_-_starts_in_the_past.html"))
        downloader.downloadSuspendable("amemiya-san").writeToFileSuspendable(resourceFile("file_converter_tests/status/range_to_unknown_-_starts_same_year.html"))
        downloader.downloadSuspendable("the-elder-sister-like-one").writeToFileSuspendable(resourceFile("file_converter_tests/status/tba.html"))
        downloader.downloadSuspendable("laid-back-camp-2nd-season").writeToFileSuspendable(resourceFile("file_converter_tests/status/year_is_current_year.html"))
        downloader.downloadSuspendable("attack-on-titan-the-final-season-part-ii").writeToFileSuspendable(resourceFile("file_converter_tests/status/year_in_the_future.html"))
        downloader.downloadSuspendable("banner-of-the-stars").writeToFileSuspendable(resourceFile("file_converter_tests/status/year_in_the_past.html"))
    
        downloader.downloadSuspendable("star-blazers-space-battleship-yamato-2202-movie").writeToFileSuspendable(resourceFile("file_converter_tests/synonyms/multiple_synonyms.html"))
        downloader.downloadSuspendable("your-name").writeToFileSuspendable(resourceFile("file_converter_tests/synonyms/synonyms_available.html"))
        downloader.downloadSuspendable("black-clover").writeToFileSuspendable(resourceFile("file_converter_tests/synonyms/synonyms_not_available.html"))
        downloader.downloadSuspendable("gj-bu-at").writeToFileSuspendable(resourceFile("file_converter_tests/synonyms/synonyms_replaced_by_email_protected.html"))
    
        downloader.downloadSuspendable("mainichi-jk-kikaku").writeToFileSuspendable(resourceFile("file_converter_tests/tags/no_tags.html"))
        downloader.downloadSuspendable("the-promised-neverland").writeToFileSuspendable(resourceFile("file_converter_tests/tags/various_tags.html"))
    
        downloader.downloadSuspendable("pripara-movie-minna-no-akogare-lets-go-prix-paris").writeToFileSuspendable(resourceFile("file_converter_tests/title/special_chars.html"))
        downloader.downloadSuspendable("malice-doll").writeToFileSuspendable(resourceFile("file_converter_tests/title/title_replaced_by_email_protected.html"))
        downloader.downloadSuspendable("the-idolmaster-million-live").writeToFileSuspendable(resourceFile("file_converter_tests/title/title_replaced_by_email_protected_no_jsonld.html"))
    
        downloader.downloadSuspendable("natsumes-book-of-friends-season-6-specials").writeToFileSuspendable(resourceFile("file_converter_tests/type/dvd_special.html"))
        downloader.downloadSuspendable("your-name").writeToFileSuspendable(resourceFile("file_converter_tests/type/movie.html"))
        downloader.downloadSuspendable("shelter").writeToFileSuspendable(resourceFile("file_converter_tests/type/music_video.html"))
        downloader.downloadSuspendable("soredemo-ayumu-wa-yosetekuru").writeToFileSuspendable(resourceFile("file_converter_tests/type/no_braces.html"))
        downloader.downloadSuspendable("gintama-jump-festa-2015").writeToFileSuspendable(resourceFile("file_converter_tests/type/other.html"))
        downloader.downloadSuspendable("rurouni-kenshin-tsuiokuhen").writeToFileSuspendable(resourceFile("file_converter_tests/type/ova.html"))
        downloader.downloadSuspendable("fullmetal-alchemist-brotherhood").writeToFileSuspendable(resourceFile("file_converter_tests/type/tv.html"))
        downloader.downloadSuspendable("hanamonogatari").writeToFileSuspendable(resourceFile("file_converter_tests/type/tv_special.html"))
        downloader.downloadSuspendable("planetarian").writeToFileSuspendable(resourceFile("file_converter_tests/type/web.html"))
    }
}

private fun resourceFile(file: String): Path {
    return Paths.get(
        testResource(file).toAbsolutePath()
            .toString()
            .replace("/build/resources/test/", "/src/test/resources/")
    )
}