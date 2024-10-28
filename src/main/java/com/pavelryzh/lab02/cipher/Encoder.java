package com.pavelryzh.lab02.cipher;

import java.io.*;

public class Encoder {

    /**
     * Encoder class uses VinegereCipher.kt, which I took from my mini-project
     * it encodes the string by key with an individual offset for each letter, so it is much more secure than the Caesar cipher
     * */

    static final String key = "ABCDA";
    private File file;

    public Encoder(File file) {
        this.file = file;
    }

    public void encodeFile() throws IOException {
        StringBuilder encodedMatrix = new StringBuilder();
        String line;

        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            encodedMatrix.append(new VigenereCipher(line, key).getCipherNumber());
            encodedMatrix.append("\n");
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(encodedMatrix.toString());
        writer.flush();  // flush - данные на диск
        writer.close();
    }

    public String decodeFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        StringBuilder decodedMatrix = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            decodedMatrix.append(new VigenereCipher(line, key).getDecodeNumber());
            decodedMatrix.append("\n");
        }
        reader.close();

        return decodedMatrix.toString();
    }
}
