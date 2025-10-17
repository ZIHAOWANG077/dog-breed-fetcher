package dogapi;

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


    public static int getNumberOfSubBreeds(String breed, BreedFetcher breedFetcher) {
        try {
            List<String> subs = breedFetcher.getSubBreeds(breed);
            return subs.size();
        } catch (BreedFetcher.BreedNotFoundException e) {
            return 0;
        }
    }
}

