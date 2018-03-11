package net.konsolas.codecrack.crack;

import net.konsolas.codecrack.cipher.Cipher;

/**
 * Interface used to define an optimisation method which generates an optimisation result from a cipher, ciphertext and
 * a fitness function that operates on the plaintext.
 */
public interface Optimiser {
    /**
     * Functional interface providing a function to return the fitness of plaintext string.
     * This is the value optimised by the {@link Optimiser}s.
     *
     * IMPORTANT: Lower fitnesses are considered better.
     */
    @FunctionalInterface
    interface FitnessFunction {
        double getFitness(char[] plaintext);
    }

    /**
     * Find the optimal key for the given {@code cipher} and {@code ciphertext},
     * optimising for the function {@code fitnessFunction}
     *
     * @param cipher cipher used to decrypt
     * @param ciphertext ciphertext to decrypt
     * @param fitnessFunction function to determine plaintext fitness
     * @return result of optimisation attempt
     */
    OptimisationResult optimise(Cipher cipher, char[] ciphertext, FitnessFunction fitnessFunction);
}
