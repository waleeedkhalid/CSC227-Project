package utils;

import job.PCB;

import java.util.ArrayList;
import java.util.List;

public class GanttChart {
    public static void displayGanttChart(List<ExecutionEvent> executionLog) {
        System.out.println("\nGantt Chart:");

        List<Integer> timePositions = new ArrayList<>();
        int chartPosition = 0;

        // Top border
        System.out.print(" ");
        for (ExecutionEvent event : executionLog) {
            int burst = event.endTime - event.startTime;
            System.out.print("─".repeat(burst * 2));
            System.out.print(" ");
        }
        System.out.println();

        // Process bar and record positions
        System.out.print("|");
        chartPosition = 1; // Starts after the initial '|'
        timePositions.add(0); // The 0 starts at the very beginning

        for (ExecutionEvent event : executionLog) {
            int burst = event.endTime - event.startTime;
            String label = "P" + event.getProcessId();
            int boxWidth = burst * 2;
            int leftPadding = (boxWidth - label.length()) / 2;
            int rightPadding = boxWidth - leftPadding - label.length();

            chartPosition += boxWidth + 1; // +1 for the ending '|'
            timePositions.add(chartPosition - 1); // Position of the next '|'

            System.out.print(" ".repeat(leftPadding) + label + " ".repeat(rightPadding) + "|");
        }
        System.out.println();

        // Bottom border
        System.out.print(" ");
        for (ExecutionEvent event : executionLog) {
            int burst = event.endTime - event.startTime;
            System.out.print("─".repeat(burst * 2));
            System.out.print(" ");
        }
        System.out.println();

        // Time markers
        StringBuilder timeLine = new StringBuilder();
        int currentTime = 0;
        for (int i = 0; i < timePositions.size(); i++) {
            String timeStr = String.valueOf(currentTime);
            int pos = timePositions.get(i);

            while (timeLine.length() < pos - timeStr.length()) {
                timeLine.append(" ");
            }
            timeLine.append(timeStr);
            if (i < executionLog.size()) {
                currentTime = executionLog.get(i).endTime;
            }
        }

        System.out.println(timeLine);
    }



    public static void displayStatistics(List<PCB> jobs) {
        System.out.println("\nStatistics:");
        System.out.printf("%-10s %-10s %-10s\n", "Process ID", "Turnaround(ms)", "Waiting(ms)");
        for (PCB job : jobs) {
            System.out.printf("%-10d %-10d %-10d\n",
                    job.getId(),
                    job.getTurnaroundTime(),
                    job.getWaitingTime());
        }
    }

    public static void printTimes(double averageTurnaroundTime, double averageWaitingTime) {
        System.out.println("Average Turnaround Time: " + String.format("%.2f", averageTurnaroundTime) + "ms");
        System.out.println("Average Waiting Time: " + String.format("%.2f", averageWaitingTime) + "ms");
    }
}
