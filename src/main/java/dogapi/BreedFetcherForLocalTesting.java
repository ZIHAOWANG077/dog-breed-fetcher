package dogapi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BreedFetcherForLocalTesting implements BreedFetcher {

    private int callCount = 0;

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        callCount++;

        if (breed == null) {
            throw new IllegalArgumentException("breed must not be null");
        }
        String b = breed.trim().toLowerCase();

        switch (b) {
            case "hound":
                return Arrays.asList("afghan", "basset");
            case "bulldog":
                return Arrays.asList("boston", "english", "french");
            case "beagle":
                return Collections.emptyList();
            default:
                throw new BreedNotFoundException(b);
        }
    }

    public int getCallCount() {
        return callCount;
    }
}
