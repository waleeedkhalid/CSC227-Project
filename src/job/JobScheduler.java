package job;

import queues.JobQueue;
import queues.ReadyQueue;
import utils.MemoryManager;

// Loading the jobs to ready queue should be performed in an independent thread that continuously checks
// the available space in memory to load the next job from the job queue to the ready queue.
public class JobScheduler implements Runnable {

    @Override
    public void run() {
        // Load jobs from job queue to ready queue
        while (true) {
            // Check if there is enough memory available
            if (MemoryManager.getAvailableMemory() > 0) {
                // Get the next job from the job queue
                PCB pcb = JobQueue.getNextJob();
                if ((pcb != null) && (pcb.getRequiredMemory() <= MemoryManager.getAvailableMemory())) {
                    // Add the job to the ready queue
                    ReadyQueue.addJob(pcb);
                    // Remove the job from the job queue
                    JobQueue.removeJob();
//                    System.out.println("Moved job to Ready Queue: " + pcb);
                }
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
