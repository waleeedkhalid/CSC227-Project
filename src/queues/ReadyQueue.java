package queues;

import job.PCB;
import job.PCBState;
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
            pcb.setState(PCBState.READY);
            readyQueue.add(pcb);
            MemoryManager.allocateMemory(pcb.getRequiredMemory());
            return true;
        }
        return false;
    }

    public PCB removeJob() {
        PCB pcb = readyQueue.poll();
        if(pcb != null) {
            MemoryManager.deallocateMemory(pcb.getRequiredMemory());
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


    public void printReadyQueue(){
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

