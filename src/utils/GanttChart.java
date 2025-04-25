package utils;

import job.PCB;

import java.util.List;

public class GanttChart {
    public static void displayGanttChart(List<ExecutionEvent> executionLog) {
        System.out.println("\nGantt Chart:");
        System.out.print("|");

        for (ExecutionEvent event : executionLog) {
            String processId = "P" + event.getProcessId();

            // Calculate string length
            int eventLength = Math.max(0, event.endTime - event.startTime - processId.length() + 1);

            // Print process ID centered
            int padding = Math.max(0, eventLength - processId.length());
            int leftPad = Math.max(0, padding / 2);
            int rightPad = Math.max(0, padding - leftPad);

            for (int i = 0; i < leftPad; i++) {
                System.out.print(" ");
            }
            System.out.print(processId);
            for (int i = 0; i < rightPad; i++) {
                System.out.print(" ");
            }
            System.out.print("|");
        }

        System.out.print("\n0");

        // Print time markers
        int lastPosition = 0;
        for (ExecutionEvent event : executionLog) {
            String timeMarker = Integer.toString(event.endTime);
            int position = lastPosition + (event.endTime - event.startTime) + 1;

            for (int i = 0; i < position - lastPosition - timeMarker.length(); i++) {
                System.out.print(" ");
            }

            System.out.print(timeMarker);
            lastPosition = position;
        }
        System.out.println();
    }

    public static void displayStatistics(List<PCB> jobs) {
        System.out.println("\nStatistics:");
        System.out.printf("%-10s %-10s %-10s\n", "Process ID", "Turnaround", "Waiting");
        for (PCB job : jobs) {
            System.out.printf("%-10d %-10d %-10d\n",
                    job.getId(),
                    job.getTurnaroundTime(),
                    job.getWaitingTime());
        }
    }
}
