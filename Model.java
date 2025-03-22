import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private int memoryBlocks;
    private int cacheHits;
    private int cacheMisses;
    private int memoryAccessCount;
    private double totalMemoryAccessTime;
    private String[][] cacheMemory;
    private List<String[][]> cacheMemorySnapshots;
    private List<String> cacheMemoryTraceLog;
    
    private LinkedHashMap<Integer, Integer> lruCache = new LinkedHashMap<>(32, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > 32; 
        }
    };

    private static final double CACHE_HIT_TIME = 1.0;
    private static final double CACHE_MISS_TIME = 10.0;
    private static final int CACHE_SIZE = 32;

    public Model() {
        cacheMemory = new String[CACHE_SIZE][16];
        cacheMemorySnapshots = new ArrayList<>();
        cacheMemoryTraceLog = new ArrayList<>();
        resetStatistics();
    }

    public void resetStatistics() {
        cacheHits = 0;
        cacheMisses = 0;
        memoryAccessCount = 0;
        totalMemoryAccessTime = 0.0;
        lruCache.clear();
        cacheMemorySnapshots.clear();
        cacheMemoryTraceLog.clear();
    }

    public void simulate(String testCase, int memoryBlocks) {
        this.memoryBlocks = memoryBlocks;
        resetStatistics();
        switch(testCase) {
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
   
        for(int i = 0; i < 4 * CACHE_SIZE; i++) {
            int block = (i * (memoryBlocks / CACHE_SIZE)) % memoryBlocks; 
            accessMemory(block);
        }
    }

    private void simulateRandomSequence() {
        for(int i = 0; i < 4 * CACHE_SIZE; i++) {
            int block = (int)(Math.random() * memoryBlocks); 
            accessMemory(block);
        }
    }

    private void simulateMidRepeatBlocks() {
      
        for(int i = 0; i < CACHE_SIZE; i++) {
            int block = (i * (memoryBlocks / CACHE_SIZE)) % memoryBlocks; 
            accessMemory(block);
        }

      
        int midStart = memoryBlocks / 2; 
        for(int j = 0; j < 2; j++) {
            for(int i = midStart; i < midStart + CACHE_SIZE; i++) {
                int block = i % memoryBlocks; 
                accessMemory(block);
            }
        }

   
        for(int i = 2 * CACHE_SIZE; i < 4 * CACHE_SIZE; i++) {
            int block = (i * (memoryBlocks / CACHE_SIZE)) % memoryBlocks;
            accessMemory(block);
        }
    }

    private void accessMemory(int block) {
        memoryAccessCount++;
        boolean isHit = lruCache.containsKey(block);

        if(isHit) {
            cacheHits++;
            totalMemoryAccessTime += CACHE_HIT_TIME;
            lruCache.get(block); 
        } else {
            cacheMisses++;
            totalMemoryAccessTime += CACHE_MISS_TIME;
            lruCache.put(block, block);
        }

        String[][] snapshot = deepCopyCacheMemory();
        cacheMemorySnapshots.add(snapshot);
        
        String logEntry = "Step " + memoryAccessCount + ": Accessed Block " + block +
                " (Cache " + (isHit ? "Hit" : "Miss") + ")";
        cacheMemoryTraceLog.add(logEntry);
    }

    private String[][] deepCopyCacheMemory() {
        String[][] copy = new String[CACHE_SIZE][16];
        for(int i = 0; i < CACHE_SIZE; i++) {
            System.arraycopy(cacheMemory[i], 0, copy[i], 0, 16);
        }
        return copy;
    }

    public int getMemoryAccessCount() { return memoryAccessCount; }
    public int getCacheHits() { return cacheHits; }
    public int getCacheMisses() { return cacheMisses; }
    public double getCacheHitRate() { return (double) cacheHits / memoryAccessCount; }
    public double getCacheMissRate() { return (double) cacheMisses / memoryAccessCount; }
    public double getAverageMemoryAccessTime() { return totalMemoryAccessTime / memoryAccessCount; }
    public double getTotalMemoryAccessTime() { return totalMemoryAccessTime; }
    public String[][] getCacheMemory() { return cacheMemory; }
    public List<String[][]> getCacheMemorySnapshots() { return cacheMemorySnapshots; }
    public List<String> getCacheMemoryTraceLog() { return cacheMemoryTraceLog; }
}