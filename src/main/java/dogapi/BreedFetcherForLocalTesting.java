package dogapi;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class BreedFetcherForLocalTesting implements BreedFetcher {

    @Override
    public List<String> getSubBreeds(String breed)
            throws BreedNotFoundException, IOException {
        if (breed == null) {
            throw new IOException("breed must not be null");
        }
        String b = breed.trim().toLowerCase();

        switch (b) {
            case "hound":
                return Arrays.asList("afghan", "basset", "blood", "english", "ibizan", "plott", "walker");
            case "bulldog":
                return Arrays.asList("boston", "english", "french");
            case "beagle":
                return Collections.emptyList();
            case "cat":
                throw new BreedNotFoundException(b);
            default:

                return Collections.emptyList();
        }
    }
}
