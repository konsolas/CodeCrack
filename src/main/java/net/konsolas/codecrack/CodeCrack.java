package net.konsolas.codecrack;

import net.konsolas.codecrack.cipher.ciphers.CipherVigenere;
import net.konsolas.codecrack.crack.OptimisationResult;
import net.konsolas.codecrack.crack.methods.EvolutionaryOptimiser;
import net.konsolas.codecrack.english.QuadgramDB;

import java.io.IOException;

public class CodeCrack {
    public static void main(String[] args) throws IOException {
        String ciphertext = "DYIMXMESTEZDPNFVVAMJRNPSGCIGRVVFSIADICUYOGYFYFMQQIFYFSMLQFXFZBUOCSOVMECWMDDBNXUEDCIAQVHFZAGORPKZCEWFRQJIANKLQGIYERFXPQADVPXTORXTIUAZBFZHQONBBPSGEPRITEZYWGLVXDFAGOURAYMBPPSGCIYVZIENIALVXDFAGOUXTPGOOCMQGMGRRXFSMLMRROSMNDPSGMCGDYIKOWAYKYZOMECKEZOBUKKCAFKNXFRXJKUORXUYITKDIMYLGRZWUDVBDRKMXMVPPSGEZLDFTXLGNQRQQZNPRVWEWQXOPSGCIYVZIEHMJSCPRTVQYLVEPTIOJMZLAGKCIYLBRIFYTLDRLVIZHIEXVHEZTRDLWEEIEDRKMTVVMRRXPBLYLLMGMGRVEPOZRCJSRLVBDYIDDISOYSGDMNDRWYLTYNZWOZCADFRAFZBBZKUYIYZIMOPIANZAUWTVXTPGOMGRVMPPVGSKCAQIOBZXUDPQYLFXPITOEXIZZXSEKUYGBEIIYMIFCPWTLTYGVWMJNBEILGYLEOUXTZCFKEHRCIAMJXAMMCKZHPTZRMKPKEWNXRGOZCADFJYJKUYFWUYOVPPSGHIADKSWYWJWFVQLJBEKXTPBEORGTPZLYWCAFZFYTEXWMQPIMQYLFDYIZWMGEJQQPBVXKLQAIEUSCFSMOBZXUDPRWSEEDGBXWVUOILKKIXPDRX";

        QuadgramDB quadgramDB = new QuadgramDB();

        //SimulatedAnnealingOptimiser optimiser = new SimulatedAnnealingOptimiser(10000000,
        //        SimulatedAnnealingOptimiser.getTempFunction(0.2, 32), SimulatedAnnealingOptimiser.P_FUNCTION_EXP);
        EvolutionaryOptimiser optimiser = new EvolutionaryOptimiser(1000, 1000, 0.5, 0.7, 0.3);

        long start = System.nanoTime();

        OptimisationResult result = optimiser.optimise(new CipherVigenere(), ciphertext.toCharArray(), quadgramDB::scoreText);

        long time = System.nanoTime() - start;

        System.out.println(result);
        System.out.println((time / 1000000D) + "ms");
    }
}
