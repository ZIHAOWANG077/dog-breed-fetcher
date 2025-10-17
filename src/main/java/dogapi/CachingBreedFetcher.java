package dogapi;

import java.util.*;

public class CachingBreedFetcher implements BreedFetcher {

    private final BreedFetcher delegate;
    private final Map<String, List<String>> cache = new HashMap<>();
    private int callsMade = 0;

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.delegate = Objects.requireNonNull(fetcher, "delegate fetcher must not be null");
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (cache.containsKey(breed)) {
            return new ArrayList<>(cache.get(breed));
        }

        callsMade++;
        try {
            List<String> result = delegate.getSubBreeds(breed);
            cache.put(breed, Collections.unmodifiableList(new ArrayList<>(result)));
            return new ArrayList<>(result);
        } catch (BreedNotFoundException e) {
            throw e;
        }
    }

    public int getCallsMade() {
        return callsMade;
    }
}

