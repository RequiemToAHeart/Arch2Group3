import java.util.ArrayList;
import java.util.List;

public class Model {
    private int memoryBlocks;
    private int cacheHits;
    private int cacheMisses;
    private int memoryAccessCount;
    private double totalMemoryAccessTime;
    private String[][] cacheMemory; // Represents the cache (32 blocks x 2 columns: Block and Data)
    private List<String[][]> cacheMemorySnapshots; // Stores cache memory state at each step
    private List<String> cacheMemoryTraceLog; // Stores text log of cache memory trace

    // Constants for timing (in nanoseconds)
    private static final double CACHE_HIT_TIME = 1.0; // Time for a cache hit
    private static final double CACHE_MISS_TIME = 10.0; // Time for a cache miss

    public Model() {
        cacheMemory = new String[32][2]; // Initialize cache memory (32 blocks x 2 columns)
        cacheMemorySnapshots = new ArrayList<>();
        cacheMemoryTraceLog = new ArrayList<>();
        resetStatistics();
    }

    public void resetStatistics() {
        cacheHits = 0;
        cacheMisses = 0;
        memoryAccessCount = 0;
        totalMemoryAccessTime = 0.0;
        cacheMemorySnapshots.clear();
        cacheMemoryTraceLog.clear();

        // Initialize cacheMemory with empty strings
        for (int i = 0; i < 32; i++) {
            cacheMemory[i][0] = ""; // Block number
            cacheMemory[i][1] = ""; // Data (first word of the block)
        }
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
        int set = block % 8; // 8 sets for 4-way BSA
        int way = (block / 8) % 4; // 4 ways per set

        if (cacheMemory[set * 4 + way][0] != null && !cacheMemory[set * 4 + way][0].isEmpty()) {
            // Cache hit
            cacheHits++;
            totalMemoryAccessTime += CACHE_HIT_TIME;
        } else {
            // Cache miss
            cacheMisses++;
            totalMemoryAccessTime += CACHE_MISS_TIME;

            // Update cacheMemory with new block data
            cacheMemory[set * 4 + way][0] = String.valueOf(block); // Block number
            cacheMemory[set * 4 + way][1] = String.valueOf(block * 16); // First word of the block
        }

        // Capture cache memory snapshot
        String[][] snapshot = deepCopyCacheMemory();
        cacheMemorySnapshots.add(snapshot);

        // Add to text log
        String logEntry = "Step " + memoryAccessCount + ": Accessed Block " + block +
                " (Cache " + (cacheMemory[set * 4 + way][0] != null && !cacheMemory[set * 4 + way][0].isEmpty() ? "Hit" : "Miss") + ")";
        cacheMemoryTraceLog.add(logEntry);
    }

    private String[][] deepCopyCacheMemory() {
        String[][] copy = new String[32][2];
        for (int i = 0; i < 32; i++) {
            System.arraycopy(cacheMemory[i], 0, copy[i], 0, 2);
        }
        return copy;
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

    public List<String[][]> getCacheMemorySnapshots() {
        return cacheMemorySnapshots;
    }

    public List<String> getCacheMemoryTraceLog() {
        return cacheMemoryTraceLog;
    }
}