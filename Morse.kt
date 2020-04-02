@file:JvmName("MorseKt")
import java.io.File
import javax.sound.sampled.AudioSystem

fun playMorse(morse: String, dotFile: String, lineFile: String) {
    for ((i, c) in morse.withIndex()) {
        when (c) {
            '.' -> playSound(dotFile)
            '-' -> playSound(lineFile)
            ' ' -> pause(20)
        }
        pause(10 + (-2..2).random())
        println("$c / $i / ${morse.length}")
    }
}

fun textToMorse(text: String) : String {
    val chars = mapOf(
            'a' to ".-", 'b' to "-...", 'c' to "-.-.", 'd' to "-..",
            'e' to ".", 'f' to "..-.", 'g' to "--.", 'h' to "....", 'i' to "..",
            'j' to ".---", 'k' to "-.-", 'l' to ".-..", 'm' to "--", 'n' to "-.",
            'o' to "---", 'p' to ".--.", 'q' to "--.-", 'r' to ".-.", 's' to "...",
            't' to "-", 't' to "-", 'u' to "..-", 'v' to "...-", 'w' to ".--",
            'x' to "-..-", 'y' to "-.--", 'z' to "--..",
            'ä' to ".-.-", 'ö' to "---.", 'ü' to "..--",
            '1' to ".----", '2' to "..---", '3' to "...--", '4' to "....-",
            '5' to ".....", '6' to "-....", '7' to "--...", '8' to "---..",
            '9' to "----.", '0' to "-----",
            '?' to "..--..", ',' to "--..--", '.' to ".-.-.-", ';' to "-.-.-", '!' to "-.-.--")
    var result = mutableListOf<String>()
    for (c in text.toLowerCase()) {
        result.add(chars.getOrDefault(c, ""))
    }
    return result.joinToString(" ")
}

fun pause(length: Int) {
    Thread.sleep(100 * (length + 1L))
}

fun playSound(file: String) {
    val clip = AudioSystem.getClip()
    val audioInputStream = AudioSystem.getAudioInputStream(File(file))
    clip.open(audioInputStream)
    clip.start()
}

fun main() {

    val text = mapOf(
        "max-1" to "Ich war voll konzentriert, das Geschnätzelte in Butter zu braten, damit der Hauptgang rechtzeitig bereit ist",
        "max-2" to "Durch die Zwiebeln waren meine Augen vernebelt und ich sah nichts",
        "frida-1" to "Ich nahm die Pizzette für den Apero aus dem Backofen",
        "frida-2" to "Er war bestimmt 350 Grad heiss und ich wollte mich nicht verbrennen",
        "frida-3" to "Ich konnte mich nicht umsehen",
        "fridolin-1" to "Ich war im Stress, weil der marktfrische Blattsalat unbedingt fertig sein muss",
        "fridolin-2" to "Der Lärm des sprudelnden heissen Wassers war so laut, dass ich nichts hörte",
        "mia-1" to "Die gelieferten, mehligen Kartoffeln waren so mühsam für die Rösti",
        "mia-2" to "Ich hatte so viel Mühe beim Raffeln, dass ich nicht auf die Ringe aufpassen konnte"
    )

    // playMorse(textToMorse("viel glück"), "audio/toms.aiff", "audio/crash_cymbal.aiff")

    playMorse(textToMorse(text.getOrDefault("mia-2", "")), "audio/toms.aiff", "audio/crash_cymbal.aiff")
}
