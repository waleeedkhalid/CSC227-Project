package job;

import java.util.LinkedList;
import java.util.Queue;

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