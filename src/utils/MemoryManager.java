package utils;

public class MemoryManager {
    private static int availableMemory = 512;

    public static int getAvailableMemory() {
        return availableMemory;
    }

    public static void allocateMemory(int memory) {
        availableMemory -= memory;
    }

    public static void deallocateMemory(int memory) {
        availableMemory += memory;
    }
}
