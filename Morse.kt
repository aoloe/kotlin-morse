@file:JvmName("MorseKt")
import java.io.File
import javax.sound.sampled.AudioSystem

fun playMorse(morse: String, dotFile: String, lineFile: String) {
    for (c in morse) {
        when (c) {
            '.' -> playSound(dotFile)
            '-' -> playSound(lineFile)
            ' ' -> pause(12)
        }
        pause(6)
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
            '9' to "----.", '0' to "-----")
    var result = mutableListOf<String>()
    for (c in text) {
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
    playMorse(textToMorse("viel glück"), "audio/toms.aiff", "audio/crash_cymbal.aiff")
}
