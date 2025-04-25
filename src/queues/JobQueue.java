package queues;

import job.PCB;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class JobQueue {
    private static final Queue<PCB> jobQueue = new ConcurrentLinkedQueue<>();

    public static void addJob(PCB pcb) {
        jobQueue.add(pcb);
    }

    public static void removeJob() {
        if (!jobQueue.isEmpty()) {
            jobQueue.remove();
        }
    }

    public static synchronized PCB getNextJob() {
        return jobQueue.peek();
    }

    public static boolean isEmpty() {
        return jobQueue.isEmpty();
    }

    public static int getSize() {
        return jobQueue.size();
    }

    public static Queue<PCB> getJobQueue() {
        return jobQueue;
    }
}
