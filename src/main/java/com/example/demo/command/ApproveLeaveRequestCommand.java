package com.example.demo.command;

import com.example.demo.service.LeaveRequestService;

public class ApproveLeaveRequestCommand implements LeaveRequestCommand {
    private final LeaveRequestService leaveRequestService;
    private final int requestId;
    private final String comments;

    public ApproveLeaveRequestCommand(LeaveRequestService leaveRequestService, int requestId, String comments) {
        this.leaveRequestService = leaveRequestService;
        this.requestId = requestId;
        this.comments = comments;
    }

    @Override
    public void execute() {
        leaveRequestService.approveRequest(requestId, comments);
    }

}

