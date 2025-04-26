package queues;

import job.PCB;
import job.PCBState;
import utils.MemoryManager;

import java.util.LinkedList;
import java.util.Queue;

public class ReadyQueue {
    private static final Queue<PCB> readyQueue = new LinkedList<>();

    public static void addJob(PCB pcb) {
        if (pcb.getRequiredMemory() <= MemoryManager.getAvailableMemory()) {
            pcb.setState(PCBState.READY);
            readyQueue.add(pcb);
            MemoryManager.allocateMemory(pcb.getRequiredMemory());
        } else {
            System.out.println("\n\nREADY QUEUE FULL");
            System.out.println("Not enough memory to add job: " + pcb.getId());
            System.out.println("Required Memory: " + pcb.getRequiredMemory());
            System.out.println("Available Memory: " + MemoryManager.getAvailableMemory());
            System.out.println("\n\n");
        }
    }

    public static PCB getNextJob() {
        return readyQueue.peek();
    }

    public static PCB removeJob() {
        PCB pcb = readyQueue.poll();
        if(pcb != null) {
            MemoryManager.deallocateMemory(pcb.getRequiredMemory());
        }
        return pcb;
    }

    public static boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    public static int getSize() {
        return readyQueue.size();
    }

    public static Queue<PCB> getJobs() {
        return readyQueue;
    }

    public static Queue<PCB> getReadyQueue() {
        return readyQueue;
    }

    public static void printReadyQueue(){
        if (readyQueue.isEmpty()) {
            System.out.println("Ready Queue is empty.");
        } else {
            System.out.println("Ready Queue:");
            for (PCB pcb : readyQueue) {
                System.out.println("Process ID: " + pcb.getId() + ", Required Memory: " + pcb.getRequiredMemory());
            }
        }
    }
}

