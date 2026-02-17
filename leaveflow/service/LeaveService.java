package com.leaveflow.service;

import com.leaveflow.model.LeaveRequest;
import com.leaveflow.model.User;
import java.util.List;

public interface LeaveService {

    /**
     * Creates a new leave request.
     * This will also handle translation logic.
     */
    void applyForLeave(LeaveRequest leaveRequest, User user);

    /**
     * Approves a leave request.
     */
    void approveRequest(int requestId);

    /**
     * Rejects a leave request.
     */
    void rejectRequest(int requestId);

    /**
     * Gets all leave requests for a specific employee.
     */
    List<LeaveRequest> getRequestsForEmployee(int userId);

    /**
     * Gets all requests with a 'PENDING' status for the HR dashboard.
     */
    List<LeaveRequest> getAllPendingRequests();
    
    /**
     * Your bonus feature: Auto-approve logic.
     */
    void autoApprovePendingRequests();
}