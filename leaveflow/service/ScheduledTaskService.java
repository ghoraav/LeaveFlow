package com.leaveflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledTaskService {

    @Autowired
    private LeaveService leaveService;

    /**
     * Runs the auto-approval logic.
     * This cron expression means "run at 2:00 AM every day".
     * (Second, Minute, Hour, Day-of-Month, Month, Day-of-Week)
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void runAutoApproval() {
        System.out.println("Running scheduled auto-approval task...");
        try {
            leaveService.autoApprovePendingRequests();
            System.out.println("Auto-approval task finished.");
        } catch (Exception e) {
            System.err.println("Error during auto-approval task: " + e.getMessage());
        }
    }
}