package utils;

import job.PCB;
import queues.JobQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReading implements Runnable {

    @Override
    public void run() {
        System.out.println();
        System.out.println("Reading file");
        read();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("job.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[:;]");

                // Check if the line format is correct
                if (parts.length != 4) {
                    System.out.println("Error: Invalid format in line: " + line);
                    continue;
                }
                try {
                    String processID = parts[0];
                    int burstTime = Integer.parseInt(parts[1]);
                    int priority = Integer.parseInt(parts[2]);
                    int memoryRequired = Integer.parseInt(parts[3]);

                    if (memoryRequired > MemoryManager.MEMORY_SIZE) {
                        System.out.println("ERR: Memory required exceeds available memory for process ID: " + processID);
                        continue;
                    }

                    // Check negative numbers
                    if (burstTime < 0 || priority < 0 || memoryRequired < 0) {
                        System.out.println("ERR: Negative values are not allowed for process ID: " + processID);
                        continue;
                    }

                    else {
                        PCB pcb = new PCB(processID, burstTime, priority, memoryRequired);
                        System.out.println(pcb);
                        JobQueue.addJob(pcb);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERR:" + line);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
