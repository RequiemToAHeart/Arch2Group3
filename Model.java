public class Model {
    private int memoryBlocks;
    private int cacheHits;
    private int cacheMisses;
    private int memoryAccessCount;
    private double totalMemoryAccessTime; // Total memory access time
    private String[][] cacheMemory; // Represents the cache (32 blocks x 16 words)

    // Constants for timing (in nanoseconds)
    private static final double CACHE_HIT_TIME = 1.0; // Time for a cache hit
    private static final double CACHE_MISS_TIME = 10.0; // Time for a cache miss

    public Model() {
        cacheMemory = new String[32][16]; // Initialize cache memory
        resetStatistics();
    }

    public void resetStatistics() {
        cacheHits = 0;
        cacheMisses = 0;
        memoryAccessCount = 0;
        totalMemoryAccessTime = 0.0;
    }

    public void simulate(String testCase, int memoryBlocks) {
        this.memoryBlocks = memoryBlocks;
        resetStatistics();

        // Placeholder logic for simulation
        switch (testCase) {
            case "Sequential Sequence":
                simulateSequentialSequence();
                break;
            case "Random Sequence":
                simulateRandomSequence();
                break;
            case "Mid-Repeat Blocks":
                simulateMidRepeatBlocks();
                break;
        }
    }

    private void simulateSequentialSequence() {
        // Simulate sequential access pattern
        for (int i = 0; i < 2 * 32; i++) { // 2n cache blocks
            accessMemory(i % memoryBlocks);
        }
    }

    private void simulateRandomSequence() {
        // Simulate random access pattern
        for (int i = 0; i < 4 * 32; i++) { // 4n cache blocks
            int block = (int) (Math.random() * memoryBlocks);
            accessMemory(block);
        }
    }

    private void simulateMidRepeatBlocks() {
        // Simulate mid-repeat access pattern
        for (int i = 0; i < 32 - 1; i++) { // Up to n-1 blocks
            accessMemory(i % memoryBlocks);
        }
        for (int i = 0; i < 32; i++) { // Repeat middle sequence
            accessMemory(i % memoryBlocks);
        }
        for (int i = 32; i < 2 * 32; i++) { // Up to 2n blocks
            accessMemory(i % memoryBlocks);
        }
    }

    private void accessMemory(int block) {
        memoryAccessCount++;
        if (block % 4 == 0) { // Placeholder logic for cache hit/miss
            cacheHits++;
            totalMemoryAccessTime += CACHE_HIT_TIME; // Add cache hit time
        } else {
            cacheMisses++;
            totalMemoryAccessTime += CACHE_MISS_TIME; // Add cache miss time
            // Update cache memory (LRU logic goes here)
        }
    }

    public int getMemoryAccessCount() {
        return memoryAccessCount;
    }

    public int getCacheHits() {
        return cacheHits;
    }

    public int getCacheMisses() {
        return cacheMisses;
    }

    public double getCacheHitRate() {
        return (double) cacheHits / memoryAccessCount;
    }

    public double getCacheMissRate() {
        return (double) cacheMisses / memoryAccessCount;
    }

    public double getAverageMemoryAccessTime() {
        return totalMemoryAccessTime / memoryAccessCount;
    }

    public double getTotalMemoryAccessTime() {
        return totalMemoryAccessTime;
    }

    public String[][] getCacheMemory() {
        return cacheMemory;
    }
}