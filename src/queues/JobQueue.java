package queues;

import job.PCB;

import java.util.LinkedList;
import java.util.Queue;

public class JobQueue {
    private static final Queue<PCB> jobQueue = new LinkedList<>();

    public static void addJob(PCB pcb) {
        jobQueue.add(pcb);
    }

    public static void removeJob() {
        if (!jobQueue.isEmpty()) {
            jobQueue.remove();
        }
    }

    public static PCB getNextJob() {
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
