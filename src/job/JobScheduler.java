package job;

import queues.JobQueue;
import queues.ReadyQueue;
import utils.MemoryManager;

import java.util.LinkedList;
import java.util.Queue;

// Loading the jobs to ready queue should be performed in an independent thread that continuously checks
// the available space in memory to load the next job from the job queue to the ready queue.
public class JobScheduler implements Runnable {

    @Override
    public void run() {
        Queue<PCB> jobQueue = new LinkedList<>(JobQueue.getJobQueue());
        // Load jobs from job queue to ready queue
        System.out.println();
        while (true) {
            while (!jobQueue.isEmpty()) {
                // Check if there is enough memory available
                if (MemoryManager.getAvailableMemory() > 0) {
                    // Get the next job from the job queue
                    PCB pcb = jobQueue.poll();
                    if ((pcb != null) && (pcb.getRequiredMemory() <= MemoryManager.getAvailableMemory())) {
                        // Add the job to the ready queue
                        ReadyQueue.addJob(pcb);
                        // Remove the job from the job queue
                        jobQueue.remove(pcb);
                        System.out.println("Moved job to Ready Queue: " + pcb);
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
