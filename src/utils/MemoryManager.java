package utils;

public class MemoryManager {
    private static int availableMemory = 0;

    public static void setAvailableMemory(int availableMemory) {
        MemoryManager.availableMemory = availableMemory;
    }

    public static int getAvailableMemory() {
        return availableMemory;
    }

    // System Call to allocate memory
    public static void allocateMemory(int memory) {
        availableMemory -= memory;
    }

    // System Call to deallocate memory
    public static void deallocateMemory(int memory) {
        availableMemory += memory;
    }
}
