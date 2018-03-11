package net.konsolas.codecrack.cipher;

/**
 * Key used for decryption in a {@link Cipher}.
 * Can be passed to the {@link Cipher} object as an argument to produce plaintext.
 */
public interface DecryptionKey {
    /**
     * Modify this key to produce a new, valid key.
     * Generally the modified key should produce a similar plaintext to the current key.
     *
     * It can be assumed that this method will be called on a chain of mutations starting from a state provided by
     * {@link Cipher#getStartingKey()}
     *
     * @return mutated key
     */
    DecryptionKey mutate();

    /**
     * Generate a new key containing internal information taken randomly from both this key and {@code other}
     * Effectively generates a "child key" with "genetic information" from both parents
     *
     * @param other other key
     * @return child key
     */
    DecryptionKey crossover(DecryptionKey other);
}
