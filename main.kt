@file:Suppress("NAME_SHADOWING")

package signature
import java.io.File

const val NAME_SPACES = 10
const val STATUS_SPACES = 5

fun main() {
    val statusFileName = "/home/tym83/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/medium.txt"
    val nameFileName = "/home/tym83/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/roman.txt"

    println("Enter name and surname: ")
    val name = readLine()!!
    println("Enter person's status: ")
    val statusOfPerson = readLine()!!

    val statusAlphabet = generateAlphabet(statusFileName)
    val nameAlphabet = generateAlphabet(nameFileName)

    val nameBigLetters = generateText(name, nameAlphabet)
    val statusBigLetters = generateText(statusOfPerson, statusAlphabet)

    printResult(statusBigLetters, nameBigLetters)
}

fun printResult (
    statusBigLetters: MutableList<String>, nameBigLetters: MutableList<String>) {
    val startOfLine = "88  "
    val endOfLine = "  88"
    val border: String
    val spacesName = mutableListOf("", "")
    val spacesStatus = mutableListOf("", "")

    if (nameBigLetters[0].length > statusBigLetters[0].length) {
        spacesStatus[0] = " ".repeat((nameBigLetters[0].length - statusBigLetters[0].length) / 2)
        spacesStatus[1] = " ".repeat(spacesStatus[0].length + (nameBigLetters[0].length - statusBigLetters[0].length) % 2)
        border = "8".repeat(startOfLine.length + nameBigLetters[0].length + endOfLine.length)
    } else if (nameBigLetters[0].length < statusBigLetters[0].length) {
        spacesName[0] = " ".repeat((statusBigLetters[0].length - nameBigLetters[0].length) / 2)
        spacesName[1] = " ".repeat( spacesName[0].length + (statusBigLetters[0].length - nameBigLetters[0].length) % 2)
        border = "8".repeat(startOfLine.length + statusBigLetters[0].length + endOfLine.length)
    } else {
        border = "8".repeat(startOfLine.length + nameBigLetters[0].length + endOfLine.length)
    }

    println(border)
    for (i in nameBigLetters.indices) {
        println("$startOfLine${spacesName[0]}${nameBigLetters[i]}${spacesName[1]}$endOfLine")
    }
    for (i in statusBigLetters.indices) {
        println("$startOfLine${spacesStatus[0]}${statusBigLetters[i]}${spacesStatus[1]}$endOfLine")
    }
    print(border)
}

fun generateAlphabet (fileName: String): MutableList<MutableList<String>> {
    val nameBigLetters: MutableList<String> = File(fileName).readLines() as MutableList<String>
    val counts = nameBigLetters.first().split(" ").map { it }.toMutableList()
    nameBigLetters.removeAt(0)
    val alphabet = MutableList(counts[1].toInt()) { MutableList(2) { "" } }
    alphabet.add(0, counts)

    for (i in 1..counts[1].toInt()) {
        val (letter, width) = nameBigLetters[0].split(" ")
        nameBigLetters.removeAt(0)
        alphabet[i][0] = letter
        alphabet[i][1] = width

        for (j in 0 until counts[0].toInt()) {
            alphabet[i].add(nameBigLetters[0])
            nameBigLetters.removeAt(0)
        }
    }

    return alphabet
}

fun generateText (string: String, alphabet: MutableList<MutableList<String>>): MutableList<String> {
    val text = MutableList(string.length) { MutableList(alphabet[0][1].toInt() + 1) { "" } }
    val outputString = MutableList(alphabet[0][0].toInt()) { "" }

    val spases = if (alphabet[0][0] == "10") {
        MutableList(alphabet[0][0].toInt()) { " ".repeat(NAME_SPACES) }
    } else {
        MutableList(alphabet[0][0].toInt()) { " ".repeat(STATUS_SPACES) }
    }
    for (i in string.indices) {
        if (string[i].isWhitespace()) text[i] = spases else {
            for (j in 1..alphabet.lastIndex) {
                if (string[i].toString() in alphabet[j]) {
                    text[i] = alphabet[j].subList(2, alphabet[j].lastIndex + 1)
                }
            }
        }
    }

    for (i in 0 until alphabet[0][0].toInt()) {

        for (j in text.indices) {
            outputString[i] += text[j][i]
        }
    }

    return outputString
}
