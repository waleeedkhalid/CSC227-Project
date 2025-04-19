package job;

import queues.JobQueue;
import queues.ReadyQueue;
import utils.MemoryManager;

// Loading the jobs to ready queue should be performed in an independent thread that continuously checks
// the available space in memory to load the next job from the job queue to the ready queue.
public class JobScheduler implements Runnable {
    private JobQueue JobQueue;
    private ReadyQueue ReadyQueue;

    public JobScheduler(JobQueue jobQueue, ReadyQueue readyQueue) {
        this.JobQueue = jobQueue;
        this.ReadyQueue = readyQueue;
    }

    @Override
    public void run() {
        // Load jobs from job queue to ready queue
        while (true) {
            // Check if there is space in memory to load the next job
            if (MemoryManager.getAvailableMemory() > 0) {
                // Load the next job from the job queue to the ready queue
                PCB pcb = JobQueue.getNextJob();
                if (pcb != null) {
                    ReadyQueue.addJob(pcb);
                }
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}
