package queues;

import java.util.LinkedList;
import java.util.Queue;
import job.PCB;
public class WaitingQueue {
    private Queue<PCB> queue;

    public WaitingQueue() {
        this.queue = new LinkedList<>();
    }

    public void addJob(PCB pcb) {
        queue.add(pcb);
    }

    public PCB removeJob() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}