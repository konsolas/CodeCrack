package net.konsolas.codecrack.cipher.ciphers;

import net.konsolas.codecrack.cipher.Cipher;
import net.konsolas.codecrack.cipher.DecryptionKey;
import net.konsolas.codecrack.util.AlphaCharSet;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of a substitution cipher
 */
public class CipherSubstitution implements Cipher {
    public class SubstitutionKey implements DecryptionKey {
        private final char[] data = new char[26];

        @Override
        public DecryptionKey mutate() {
            SubstitutionKey newKey = new SubstitutionKey();
            System.arraycopy(data, 0, newKey.data, 0, 26);

            int loc1 = ThreadLocalRandom.current().nextInt(26);
            int loc2 = ThreadLocalRandom.current().nextInt(26);

            char temp1 = newKey.data[loc1];
            newKey.data[loc1] = newKey.data[loc2];
            newKey.data[loc2] = temp1;

            return newKey;
        }

        @Override
        public DecryptionKey crossover(DecryptionKey other) {
            SubstitutionKey o = (SubstitutionKey) other;
            SubstitutionKey newKey = new SubstitutionKey();
            AlphaCharSet usedChars = new AlphaCharSet();

            for(int i = 0; i < 26; i++) {
                if(ThreadLocalRandom.current().nextBoolean() && !usedChars.contains(this.data[i])) {
                    newKey.data[i] = this.data[i];
                } else if(!usedChars.contains(o.data[i])){
                    newKey.data[i] = o.data[i];
                } else {
                    throw new AssertionError();
                }
            }

            return newKey;
        }

        @Override
        public String toString() {
            // Internal storage format is optimal for fast decryption, but not an accurate key
            // Need post-processing to convert to an actual key, this is done in toString

            StringBuilder builder = new StringBuilder(26);
            builder.setLength(26);

            for(int i = 0; i < 26; i++) {
                builder.setCharAt(data[i] - 'a', (char) (i + 'A'));
            }

            return "sub/" + builder.toString().toUpperCase();
        }
    }

    @Override
    public DecryptionKey getStartingKey() {
        List<Character> alphabet = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        Collections.shuffle(alphabet);
        SubstitutionKey key = new SubstitutionKey();

        for(int i = 0; i < 26; i++) {
            key.data[i] = alphabet.get(i);
        }

        return key;
    }

    @Override
    public char[] decrypt(char[] ciphertext, DecryptionKey key) {
        SubstitutionKey subKey = (SubstitutionKey) key;

        char[] plaintext = new char[ciphertext.length];

        for(int i = 0; i < ciphertext.length; i++) {
            plaintext[i] = subKey.data[(ciphertext[i] - 'A')];
        }

        return plaintext;
    }
}
