package com.leaveflow.service;

import com.leaveflow.model.LeaveRequest;
import com.leaveflow.model.User;
import com.leaveflow.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

// @Service marks this as the implementation
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private EmailService emailService;
    // @Autowired tells Spring to "inject" the objects we need
    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private MyMemoryTranslationService translationService;

    // @Transactional ensures that if something fails, the database change is rolled back.
    @Override
    @Transactional
    public void applyForLeave(LeaveRequest leaveRequest, User user) {
        // 1. Set the user on the request
        leaveRequest.setUser(user);
        
        // 2. Set default status
        leaveRequest.setStatus("PENDING");

        // 3. Handle the translation
        String originalReason = leaveRequest.getReason();
        String language = leaveRequest.getLanguage();

        if (!"en".equalsIgnoreCase(language)) {
            String translatedReason = translationService.translateToEnglish(originalReason, language);
            leaveRequest.setTranslatedReason(translatedReason);
        } else {
            // If it's already English, just copy it
            leaveRequest.setTranslatedReason(originalReason);
        }

        // 4. Save the request to the database
        leaveRequestRepository.save(leaveRequest);
    }

    @Override
    @Transactional
    public void approveRequest(int requestId) {
        leaveRequestRepository.findById(requestId).ifPresent(request -> {
            request.setStatus("APPROVED");
            leaveRequestRepository.save(request);

            // Send Email
            String to = request.getUser().getEmail();
            String subject = "Leave Request Approved";
            String body = "Dear " + request.getUser().getUsername() + ",\n\n"
                        + "Your leave request from " + request.getStartDate() 
                        + " to " + request.getEndDate() + " has been approved."
                        + "\n\nRegards,\nLeaveFlow Admin";
            emailService.sendEmail(to, subject, body);
        });
    }

    @Override
    @Transactional
    public void rejectRequest(int requestId) {
        leaveRequestRepository.findById(requestId).ifPresent(request -> {
            request.setStatus("REJECTED");
            leaveRequestRepository.save(request);

            // Send Email
            String to = request.getUser().getEmail();
            String subject = "Leave Request Rejected";
            String body = "Dear " + request.getUser().getUsername() + ",\n\n"
                        + "Your leave request from " + request.getStartDate() 
                        + " to " + request.getEndDate() + " has been rejected."
                        + "\n\nRegards,\nLeaveFlow Admin";
            emailService.sendEmail(to, subject, body);
        });
    }

    @Override
    @Transactional(readOnly = true) // readOnly = true is an optimization for 'find' methods
    public List<LeaveRequest> getRequestsForEmployee(int userId) {
        return leaveRequestRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeaveRequest> getAllPendingRequests() {
        return leaveRequestRepository.findByStatus("PENDING");
    }
    
    @Override
    @Transactional
    public void autoApprovePendingRequests() {
        // Your auto-approval logic:
        // Find all pending requests
        List<LeaveRequest> pendingRequests = leaveRequestRepository.findByStatus("PENDING");
        
        // Define "X days" (e.g., 7 days ago)
        LocalDate autoApproveDate = LocalDate.now().minusDays(1); //for testing purposes, real value 7 days
        
        for (LeaveRequest request : pendingRequests) {
            // Check if the request was created on or before the auto-approve date
            // Note: 'createdAt' is LocalDateTime, so we convert it to LocalDate
            if (!request.getCreatedAt().toLocalDate().isAfter(autoApproveDate)) {
                request.setStatus("REJECTED (Auto)");
                leaveRequestRepository.save(request);
            }
        }
    }
}