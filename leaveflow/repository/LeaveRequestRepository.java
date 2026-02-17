package com.leaveflow.repository;

import com.leaveflow.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {

    // Finds all leave requests for a specific user, identified by their ID
    List<LeaveRequest> findByUserId(int userId);

    // Finds all leave requests that have a specific status (e.g., "PENDING")
    // This will be used by HR/Admin.
    List<LeaveRequest> findByStatus(String status);
    
}