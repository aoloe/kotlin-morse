@file:JvmName("MorseKt")
import java.io.File
import javax.sound.sampled.AudioSystem

fun playMorse(morse: String, dotFile: String, lineFile: String) {
    for ((i, c) in morse.withIndex()) {
        when (c) {
            '.' -> playSound(dotFile)
            '-' -> playSound(lineFile)
            ' ' -> pause_ms(2000) // fast = 1200
        }
        pause_ms(1000 + (-2..2).random() * 100) // fast = 600
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

fun pause_ms(length: Int) {
    Thread.sleep(length + 1L)
}

fun playSound(file: String) {
    val clip = AudioSystem.getClip()
    val audioInputStream = AudioSystem.getAudioInputStream(File(file))
    clip.open(audioInputStream)
    clip.start()
}

fun get_file_content(filename: String): String? {
    // TODO: remove the final \n
    val text_file = File(filename)
    if (text_file.exists()) {
        return text_file.readText(Charsets.UTF_8)
    }
    return null
}


fun read_text_from_args(args: Array<String>): String? {
    // TODO: also read the dot and line sounds from the command line
    // using https://github.com/Kotlin/kotlinx.cli ?
    return when (args.size) {
        2 -> if (args[0] == "-f") get_file_content(args[1]) else null
        1 -> args[0]
        else -> null
    }
}

fun main(args: Array<String>) {
    val text = read_text_from_args(args)
    if (text != null) {
        playMorse(textToMorse(text), "audio/toms.aiff", "audio/crash_cymbal.aiff")
    } else {
        println("usage:")
        println("  java -jar Morse -f textfile.txt")
        println("  java -jar Morse \"Some text\"")
    }
}
