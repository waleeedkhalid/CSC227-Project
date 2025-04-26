package utils;

public class MemoryManager {
    private static int availableMemory;
    public static final int MEMORY_SIZE = 2048; // Centralized memory size constant

    public static void setAvailableMemory(int availableMemory) {
        if (availableMemory < 0) {
            throw new IllegalArgumentException("Available memory cannot be negative");
        }
        MemoryManager.availableMemory = availableMemory;
    }

    public static int getAvailableMemory() {
        return availableMemory;
    }

    // System Call to allocate memory
    public static void allocateMemory(int memory) {
        if (memory <= availableMemory) {
            availableMemory -= memory;
        } else {
            System.out.println("Not enough memory available to allocate " + memory + " units.");
        }
    }

    // System Call to deallocate memory
    public static void deallocateMemory(int memory) {
        availableMemory += memory;
    }

    public static void resetMemory() {
        availableMemory = MEMORY_SIZE; // Reset to MEMORY_SIZE constant
    }
}
