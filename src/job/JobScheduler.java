package job;

import queues.JobQueue;
import queues.ReadyQueue;
import utils.MemoryManager;

// Loading the jobs to ready queue should be performed in an independent thread that continuously checks
// the available space in memory to load the next job from the job queue to the ready queue.
public class JobScheduler implements Runnable {
    private JobQueue JobQueue;
    private ReadyQueue ReadyQueue;
    private boolean running = true;

    public JobScheduler(JobQueue jobQueue, ReadyQueue readyQueue) {
        this.JobQueue = jobQueue;
        this.ReadyQueue = readyQueue;
    }

    @Override
    public void run() {
        // Load jobs from job queue to ready queue
        while (running) {
            // Check if there is enough memory available
            if (MemoryManager.getAvailableMemory() > 0) {
                // Get the next job from the job queue
                PCB pcb = JobQueue.getNextJob();
                if ((pcb != null) && (pcb.getRequiredMemory() <= MemoryManager.getAvailableMemory())) {
                    // Add the job to the ready queue
                    ReadyQueue.addJob(pcb);
                    // Remove the job from the job queue
                    JobQueue.removeJob();
                    System.out.println("Moved job to Ready Queue: " + pcb);
                }
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void stopScheduler() {
        running = false;
    }
}
