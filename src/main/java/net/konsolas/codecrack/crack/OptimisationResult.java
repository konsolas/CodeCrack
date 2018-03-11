package net.konsolas.codecrack.crack;

import net.konsolas.codecrack.cipher.DecryptionKey;

/**
 * Holds the result of an optimisation, including the key, best score and generated plaintext.
 */
public class OptimisationResult implements Comparable<OptimisationResult> {
    private final double score;
    private final String plaintext;
    private final DecryptionKey key;

    /**
     * Construct a new {@link OptimisationResult}
     *
     * @param score scaled final score
     * @param plaintext generated plaintext
     * @param key key used
     */
    public OptimisationResult(double score, String plaintext, DecryptionKey key) {
        this.score = score;
        this.plaintext = plaintext;
        this.key = key;
    }

    /**
     * Get the scaled score
     *
     * @return score
     */
    public double getScore() {
        return score;
    }
    /**
     * Get the plaintext
     *
     * @return plaintext
     */
    public String getPlaintext() {
        return plaintext;
    }

    /**
     * Get the key used
     *
     * @return key
     */
    public DecryptionKey getKey() {
        return key;
    }

    @Override
    public int compareTo(OptimisationResult o) {
        return Double.compare(score, o.score);
    }

    @Override
    public String toString() {
        return "(" + key + ") " + score + ": " + plaintext;
    }
}
