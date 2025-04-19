package queues;

import job.PCB;

import java.util.LinkedList;
import java.util.Queue;

public class JobQueue {
    private Queue<PCB> jobQueue;

    public JobQueue() {
        this.jobQueue = new LinkedList<>();
    }

    public boolean addJob(PCB pcb) {
        return jobQueue.add(pcb);
    }

    public PCB removeJob() {
        return jobQueue.remove();
    }

    public PCB getNextJob() {
        return jobQueue.peek();
    }

    public boolean isEmpty() {
        return jobQueue.isEmpty();
    }

    public int getSize() {
        return jobQueue.size();
    }
}
