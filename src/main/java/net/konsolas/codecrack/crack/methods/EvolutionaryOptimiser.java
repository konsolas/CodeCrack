package net.konsolas.codecrack.crack.methods;

import net.konsolas.codecrack.cipher.Cipher;
import net.konsolas.codecrack.cipher.DecryptionKey;
import net.konsolas.codecrack.crack.OptimisationResult;
import net.konsolas.codecrack.crack.Optimiser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class EvolutionaryOptimiser implements Optimiser {
    private class Organism implements Comparable<Organism> {
        private final DecryptionKey key;
        private final double fitness;

        Organism(DecryptionKey key, double fitness) {
            this.key = key;
            this.fitness = fitness;
        }

        @Override
        public int compareTo(Organism o) {
            return Double.compare(fitness, o.fitness);
        }
    }

    private final int generations;
    private final int populationSize;
    private final double deathRate;
    private final double birthRate;
    private final double mutationRate;

    public EvolutionaryOptimiser(int generations, int populationSize, double deathRate, double birthRate, double mutationRate) {
        this.generations = generations;
        this.populationSize = populationSize;
        this.deathRate = deathRate;
        this.birthRate = birthRate;
        this.mutationRate = mutationRate;
    }

    @Override
    public OptimisationResult optimise(Cipher cipher, char[] ciphertext, FitnessFunction fitnessFunction) {
        // Create the population
        List<Organism> population = new ArrayList<>(populationSize);

        for (int gen = 0; gen < generations; gen++) {
            // Repopulate
            while (population.size() < populationSize) {
                DecryptionKey key = cipher.getStartingKey();
                double fitness = fitnessFunction.getFitness(cipher.decrypt(ciphertext, key));
                population.add(new Organism(key, fitness));
            }

            // Print out best organism
            //Organism best = population.stream().min(Organism::compareTo).orElseThrow(AssertionError::new);
            //System.out.println("gen: " + gen + " / " + best.fitness);

            // Kill
            Collections.sort(population);
            population.subList((int) (populationSize - populationSize * deathRate), populationSize).clear();

            // Breed
            population.addAll(population.stream().filter(o -> birthRate > ThreadLocalRandom.current().nextDouble()).map(o -> {
                DecryptionKey newKey = o.key.crossover(population.get(ThreadLocalRandom.current().nextInt(population.size())).key);
                double fitness = fitnessFunction.getFitness(cipher.decrypt(ciphertext, newKey));
                return new Organism(newKey, fitness);
            }).limit(populationSize - population.size()).collect(Collectors.toList()));

            // Mutate
            for(int i = 0; i < population.size(); i++) {
                if(mutationRate > ThreadLocalRandom.current().nextDouble()) {
                    DecryptionKey newKey = population.get(i).key.mutate();
                    double fitness = fitnessFunction.getFitness(cipher.decrypt(ciphertext, newKey));
                    population.set(i, new Organism(newKey, fitness));
                }
            }
        }

        Organism best = population.stream().min(Organism::compareTo).orElseThrow(AssertionError::new);

        // Print population
        //population.stream().map(organism -> organism.key + " / " + organism.fitness).forEach(System.out::println);

        return new OptimisationResult(best.fitness, new String(cipher.decrypt(ciphertext, best.key)), best.key);
    }
}
