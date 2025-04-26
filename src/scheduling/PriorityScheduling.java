package scheduling;

import job.PCB;
import job.PCBState;
import queues.ReadyQueue;
import utils.MemoryManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Non-preemptive Priority Scheduling (1=lowest, 8=highest).
 * Marks a process as 'starved' if its waitingTime > its priority at launch.
 */

public class PriorityScheduling {

    public void run() throws InterruptedException {
        int currentTime = 0;
        List<PCB> finished = new ArrayList<>();

        // wait for at least one job to reach the ready queue
        while (ReadyQueue.isEmpty()) {
            Thread.sleep(50);
        }

        // pull jobs until none remain
        while (!ReadyQueue.isEmpty()) {
            // sort by ascending priority
            List<PCB> ready = new ArrayList<>(ReadyQueue.getJobs());
            ready.sort(Comparator.comparingInt(PCB::getPriority));

            PCB pcb = ready.get(0); // get the highest priority job

            // remove it, allocate its memory, and run it
            ReadyQueue.removeJob();

            MemoryManager.allocateMemory(pcb.getRequiredMemory());

            pcb.setState(PCBState.RUNNING);
            pcb.setStartTime(currentTime);

            // “run” the job
            currentTime += pcb.getBurstTime();
            pcb.setTurnaroundTime(currentTime);
            // since arrivalTime=0 for all, waiting = startTime, turnaround = completionTime
            pcb.setWaitingTime(pcb.getStartTime());

            // starvation if it waited longer (ms) than its priority value
            if (pcb.getWaitingTime() > pcb.getPriority()) {
                pcb.setStarvation(true);
            }

            pcb.setState(PCBState.TERMINATED);
            MemoryManager.deallocateMemory(pcb.getRequiredMemory());
            finished.add(pcb);

            System.out.printf(
                    "P%d  start=%d  end=%d  wait=%d  turnaround=%d  starved=%b%n",
                    pcb.getId(),
                    pcb.getStartTime(),
                    pcb.getWaitingTime(),
                    pcb.getTurnaroundTime(),
                    pcb.isStarvation()
            );
        }

        // summary
        double avgWait = finished.stream()
                .mapToInt(PCB::getWaitingTime)
                .average()
                .orElse(0);
        double avgTurn = finished.stream()
                .mapToInt(PCB::getTurnaroundTime)
                .average()
                .orElse(0);

        System.out.printf("→ Average waiting time:   %.2f ms%n", avgWait);
        System.out.printf("→ Average turnaround time: %.2f ms%n", avgTurn);
    }
}
