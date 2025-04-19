package queues;

import job.PCB;
import utils.MemoryManager;

import java.util.LinkedList;
import java.util.Queue;

public class ReadyQueue {
    private Queue<PCB> readyQueue;

    public ReadyQueue() {
        this.readyQueue = new LinkedList<>();
    }

    public boolean addJob(PCB pcb) {
        if (pcb.getRequiredMemory() <= MemoryManager.getAvailableMemory()) {
            readyQueue.add(pcb);
            MemoryManager.deallocateMemory(pcb.getRequiredMemory());
            return true;
        }
        return false;
    }

    public PCB removeJob() {
        PCB pcb = readyQueue.poll();
        if(pcb != null) {
            MemoryManager.allocateMemory(pcb.getRequiredMemory());
        }
        return pcb;
    }

    public boolean isEmpty() {
        return readyQueue.isEmpty();
    }

    public int getMemorySize() {
        return MemoryManager.getAvailableMemory();
    }

    public int getSize() {
        return readyQueue.size();
    }
}
