package dogapi;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String breed = "hound";
        BreedFetcher breedFetcher = new CachingBreedFetcher(new BreedFetcherForLocalTesting());
        try {
            int result = getNumberOfSubBreeds(breed, breedFetcher);
            System.out.println(breed + " has " + result + " sub breeds");

            breed = "cat";
            result = getNumberOfSubBreeds(breed, breedFetcher);
            System.out.println(breed + " has " + result + " sub breeds");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Return the number of sub breeds that the given dog breed has according to the provided fetcher.
     * Zero should be returned if there are no sub breeds, or the breed is invalid (not found).
     */
    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            List<String> subs = breedFetcher.getSubBreeds(breed);
            return subs.size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
