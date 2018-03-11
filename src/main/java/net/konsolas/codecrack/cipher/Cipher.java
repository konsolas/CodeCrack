package net.konsolas.codecrack.cipher;

public interface Cipher {
    /**
     * Generate a key that can be used to decrypt this cipher, to be used as the zeroth state of an optimisation algorithm.
     * The key is not relevant to any particular plaintext but can be used by optimisation algorithms to find the correct key.
     *
     * @return random generated key
     */
    DecryptionKey getStartingKey();

    /**
     * Decrypt the given {@code plaintext} as if it had been encrypted with {@code key}
     *
     * @param ciphertext plaintext
     * @param key key to decrypt
     * @return reference to {@code use}
     * @throws ClassCastException if {@code key} is the wrong type for this cipher
     */
    char[] decrypt(char[] ciphertext, DecryptionKey key);
}
