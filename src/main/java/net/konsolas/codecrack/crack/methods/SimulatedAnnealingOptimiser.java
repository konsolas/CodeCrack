package net.konsolas.codecrack.crack.methods;

import net.konsolas.codecrack.cipher.Cipher;
import net.konsolas.codecrack.cipher.DecryptionKey;
import net.konsolas.codecrack.crack.OptimisationResult;
import net.konsolas.codecrack.crack.Optimiser;

import java.util.concurrent.ThreadLocalRandom;

public class SimulatedAnnealingOptimiser implements Optimiser {
    @FunctionalInterface
    public interface TempFunction {
        double getTemp(int iterations, int maxIterations);
    }

    @FunctionalInterface
    public interface PFunction {
        double getP(double candidate, double current, double temp);
    }

    // Standard functions
    public static TempFunction getTempFunction(double hillClimbFraction, int baseTemp) {
        return (iterations, maxIterations) -> Math.max(0, (maxIterations - (int) (iterations * (1 + hillClimbFraction))) * baseTemp / maxIterations);
    }

    public static final PFunction P_FUNCTION_EXP = (candidate, current, temp) -> candidate < current ? 1D : Math.exp(-(candidate - current) / temp);

    private final int iterations;
    private final TempFunction tempFunction;
    private final PFunction pFunction;

    public SimulatedAnnealingOptimiser(int iterations, TempFunction tempFunction, PFunction pFunction) {
        this.iterations = iterations;
        this.tempFunction = tempFunction;
        this.pFunction = pFunction;
    }

    @Override
    public OptimisationResult optimise(Cipher cipher, char[] ciphertext, FitnessFunction fitnessFunction) {
        ThreadLocalRandom rng = ThreadLocalRandom.current();

        DecryptionKey s = cipher.getStartingKey();
        double sE = fitnessFunction.getFitness(cipher.decrypt(ciphertext, s));

        for(int k = 0; k < iterations; k++) {
            double temperature = tempFunction.getTemp(k, iterations);

            DecryptionKey sNew = s.mutate();
            double sNewE = fitnessFunction.getFitness(cipher.decrypt(ciphertext, sNew));

            double P = pFunction.getP(sNewE, sE, temperature);

            if(P >= rng.nextDouble()) {
                // Update state
                s = sNew;
                sE = sNewE;
            }
        }

        // Return
        return new OptimisationResult(sE, new String(cipher.decrypt(ciphertext, s)), s);
    }
}
