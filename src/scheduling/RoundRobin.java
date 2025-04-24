package scheduling;

import job.PCB;
import job.PCBState;
import queues.ReadyQueue;
import utils.ExecutionEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin {
    static List<PCB> completedJobs = new ArrayList<>();
    private static int quantumTime = 0; // QUANTUM TIME (ms)

    public static void setQuantumTime(int quantumTime) {
        RoundRobin.quantumTime = quantumTime;
    }

    public static List<ExecutionEvent> run() {
        System.out.println("\nExecuting Round-Robin (RR) Scheduling with quantum = " + quantumTime + "ms...");
        List<ExecutionEvent> executionLog = new ArrayList<>();
        Queue<PCB> queue = ReadyQueue.getReadyQueue();
        int currentTime = 0;

        // Initialize processes
        for (PCB job : queue) {
            job.setRemainingTime(job.getBurstTime());
            job.setState(PCBState.READY);
        }

        while (!queue.isEmpty()) {
            PCB job = queue.poll();

            int executionTime = Math.min(quantumTime, job.getRemainingTime());

            // Execute the job for up to quantum time units
            executionLog.add(new ExecutionEvent(job.getId(), currentTime, currentTime + executionTime));
            currentTime += executionTime;
            job.setRemainingTime(job.getRemainingTime() - executionTime);

            if (job.getRemainingTime() > 0) {
                // If job is not completed, put back in queue
                queue.add(job);
            } else {
                // Job is completed
                job.setCompletionTime(currentTime);
                job.setTurnaroundTime(job.getCompletionTime()); // Assuming arrival time is 0
                job.setWaitingTime(job.getTurnaroundTime() - job.getBurstTime());
                job.setState(PCBState.COMPLETED);
                completedJobs.add(job);
            }
        }

        // Print the execution log
        System.out.println("Execution Log:");
        for (ExecutionEvent event : executionLog) {
            System.out.println("Process " + event.getProcessId() + " executed from " + event.getStartTime() + " to " + event.getEndTime());
        }

        double avgTurnaroundTime = (currentTime / (double) completedJobs.size());
        double avgWaitingTime = (currentTime / (double) completedJobs.size());

        System.out.println("Total time taken: " + currentTime + "ms");
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime + "ms");
        System.out.println("Average Waiting Time: " + avgWaitingTime + "ms");

        return executionLog;
    }
}
