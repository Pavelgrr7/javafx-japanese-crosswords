package com.pavelryzh.lab02.cipher

class VigenereCipher(private var text: String, private var key: String) {

    private val alphabet = ('A'..'Z').toList().toTypedArray()

    init {
        text = text.uppercase()
        key = key.uppercase()

        if (text.isEmpty() || key.isEmpty()) {
            throw IllegalArgumentException("Text / key cannot be empty")
        }

        key = key.replace(" ", "")

        val keyWithoutSpaces = key.repeat((text.filter { it != ' ' }.length / key.length) + 1)
        var expandedKey = ""
        var nonSpaceIndex = 0

        text.forEach { char ->
            expandedKey += if (char == ' ') {
                " "
            } else {
                keyWithoutSpaces[nonSpaceIndex++]
            }
        }
        key = expandedKey
    }

    fun getCipherText(): String {
        var cipherText = ""

        text.forEachIndexed { index, char ->
            if (char == ' ') {
                cipherText += " "
            } else {
                val textIndex = alphabet.indexOf(char)
                val keyChar = key[index] // символ ключа на той же позиции
                val keyIndex = alphabet.indexOf(keyChar)

                val cipherChar = alphabet[(textIndex + keyIndex) % alphabet.size]
                cipherText += cipherChar
            }
        }

        return cipherText
    }

    fun getCipherNumber(): String {
        var newText = ""
        text.forEachIndexed { _, char ->
            newText += alphabet[char.code - 48]
        }
        text = newText
        return getCipherText()
    }

    fun getDecodeNumber(): String {
        var decodedText = ""

        text.forEachIndexed { index, char ->
            if (char == ' ') {
                decodedText += " "
            } else {
                val cipherIndex = alphabet.indexOf(char)
                val keyChar = key[index] // символ ключа на той же позиции
                val keyIndex = alphabet.indexOf(keyChar)

                val originalIndex = (cipherIndex - keyIndex + alphabet.size) % alphabet.size
                decodedText += alphabet[originalIndex]
            }
        }

        var decodedNumbers = ""
        decodedText.forEachIndexed { _, char ->
            decodedNumbers += (char.code - 65 + 48).toChar() // восстановление цифры
        }

        return decodedNumbers
    }
}
