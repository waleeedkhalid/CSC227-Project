package utils;

import job.PCB;

import java.util.ArrayList;
import java.util.List;

public class GanttChart {
//    public static void displayGanttChart(List<ExecutionEvent> executionLog) {
//        System.out.println("\nGantt Chart:");
//        System.out.print("|");
//
//        for (ExecutionEvent event : executionLog) {
//            String processId = "P" + event.getProcessId();
//
//            // Calculate string length
//            int eventLength = Math.max(0, event.endTime - event.startTime - processId.length() + 1);
//
//            // Print process ID centered
//            int padding = Math.max(0, eventLength - processId.length());
//            int leftPad = Math.max(0, padding / 2);
//            int rightPad = Math.max(0, padding - leftPad);
//
//            for (int i = 0; i < leftPad; i++) {
//                System.out.print(" ");
//            }
//            System.out.print(processId);
//            for (int i = 0; i < rightPad; i++) {
//                System.out.print(" ");
//            }
//            System.out.print("|");
//        }
//
//        System.out.print("\n0");
//
//        // Print time markers
//        int lastPosition = 0;
//        for (ExecutionEvent event : executionLog) {
//            String timeMarker = Integer.toString(event.endTime);
//            int position = lastPosition + (event.endTime - event.startTime) + 1;
//
//            for (int i = 0; i < position - lastPosition - timeMarker.length(); i++) {
//                System.out.print(" ");
//            }
//
//            System.out.print(timeMarker);
//            lastPosition = position;
//        }
//        System.out.println();
//    }

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
        System.out.printf("%-10s %-10s %-10s\n", "Process ID", "Turnaround", "Waiting");
        for (PCB job : jobs) {
            System.out.printf("%-10d %-10d %-10d\n",
                    job.getId(),
                    job.getTurnaroundTime(),
                    job.getWaitingTime());
        }
    }
}
