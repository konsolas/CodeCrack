package net.konsolas.codecrack.cipher.ciphers;

import net.konsolas.codecrack.cipher.Cipher;
import net.konsolas.codecrack.cipher.DecryptionKey;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of a vigenere cipher. Key length must be specified in the constructor.
 */
public class CipherVigenere implements Cipher {
    public class VigenereKey implements DecryptionKey {
        private final char data[];

        public VigenereKey(int size) {
            this.data = new char[size];
        }

        @Override
        public DecryptionKey mutate() {
            if (ThreadLocalRandom.current().nextDouble() < 0.2) {
                // Modify key length
                int newLen;
                if (this.data.length > 3) {
                    if (this.data.length < 30) {
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            newLen = this.data.length + 1;
                        } else {
                            newLen = this.data.length - 1;
                        }
                    } else {
                        newLen = this.data.length - 1;
                    }
                } else {
                    newLen = this.data.length + 1;
                }

                VigenereKey newKey = new VigenereKey(newLen);
                System.arraycopy(data, 0, newKey.data, 0, Math.min(data.length, newLen));
                if (newLen > this.data.length)
                    newKey.data[newKey.data.length - 1] = (char) (ThreadLocalRandom.current().nextInt(26) + 'A');

                return newKey;
            } else {
                // Standard mutation
                VigenereKey newKey = new VigenereKey(data.length);
                System.arraycopy(data, 0, newKey.data, 0, data.length);

                newKey.data[ThreadLocalRandom.current().nextInt(data.length)] = (char) (ThreadLocalRandom.current().nextInt(26) + 'A');

                return newKey;
            }
        }

        @Override
        public DecryptionKey crossover(DecryptionKey other) {
            VigenereKey o = (VigenereKey) other;
            // Pick a length from either key
            VigenereKey newKey = new VigenereKey(ThreadLocalRandom.current().nextBoolean() ? this.data.length : o.data.length);

            for (int i = 0; i < newKey.data.length; i++) {
                if (i >= this.data.length) newKey.data[i] = o.data[i];
                else if (i >= o.data.length) newKey.data[i] = this.data[i];
                else newKey.data[i] = ThreadLocalRandom.current().nextBoolean() ? this.data[i] : o.data[i];
            }

            return newKey;
        }

        @Override
        public String toString() {
            return "vig/" + new String(data);
        }
    }

    @Override
    public DecryptionKey getStartingKey() {
        VigenereKey key = new VigenereKey(ThreadLocalRandom.current().nextInt(3, 30));
        for (int i = 0; i < key.data.length; i++) {
            key.data[i] = (char) (ThreadLocalRandom.current().nextInt(26) + 'A');
        }

        return key;
    }

    @Override
    public char[] decrypt(char[] ciphertext, DecryptionKey key) {
        VigenereKey vigenereKey = (VigenereKey) key;

        char[] plaintext = new char[ciphertext.length];

        for (int i = 0; i < ciphertext.length; i++) {
            plaintext[i] = (char) (((ciphertext[i] - vigenereKey.data[i % vigenereKey.data.length] + 26) % 26) + 'a');
        }

        return plaintext;
    }
}
