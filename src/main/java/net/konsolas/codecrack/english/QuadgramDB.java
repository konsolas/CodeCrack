package net.konsolas.codecrack.english;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class QuadgramDB {
    private final double[] quadGrams;

    public QuadgramDB() throws IOException {
        try (InputStream in = getClass().getResourceAsStream("/qgramdb.json");
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder builder = new StringBuilder();
            String aux;

            while ((aux = bufferedReader.readLine()) != null) {
                builder.append(aux);
            }

            String fileContents = builder.toString();

            Gson gson = new Gson();

            quadGrams = gson.fromJson(fileContents, double[].class);
        }
    }

    public double scoreText(char[] message) {
        double score = 0;
        for(int i = 0; i < message.length - 3; i++) {
            int index = 0;
            index += 17576 * (message[i] - 'a');
            index += 676 * (message[i + 1] - 'a');
            index += 26 * (message[i + 2] - 'a');
            index += (message[i + 3] - 'a');

            score += quadGrams[index];
        }

        return score;
    }
}
