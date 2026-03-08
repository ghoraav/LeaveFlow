<%@ include file="header.jspf" %>

<h3>Admin Dashboard - Pending Requests</h3>

<c:choose>
    <c:when test="${empty pendingRequests}">
        <div class="alert alert-success">No pending leave requests. Good job!</div>
    </c:when>
    <c:otherwise>
        <table class="table table-hover">
            <thead class="table-light">
                <tr>
                    <th>Employee</th>
                    <th>Dates</th>
                    <th>Reason (Translated)</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="req" items="${pendingRequests}">
                    <tr>
                        <td>${req.user.username}</td>
                        <td>
                            ${req.startDate} to ${req.endDate}
                        </td>
                        <td>
                            <%-- Show the translated reason for the admin --%>
                            ${req.translatedReason}
                            
                            <%-- Show original in a small font if it was translated --%>
                            <c:if test="${req.language != 'en'}">
                                <br><small class="text-muted">Original: ${req.reason}</small>
                            </c:if>
                        </td>
                        <td>
                            <%-- 
                              These buttons are inside mini-forms.
                              This is the simplest way to send a POST request
                              with the specific 'requestId'.
                            --%>
                            <form action="${pageContext.request.contextPath}/admin/approve/${req.id}" method="post" style="display:inline-block;">
                                <button type="submit" class="btn btn-sm btn-success">Approve</button>
                            </form>
                            <form action="${pageContext.request.contextPath}/admin/reject/${req.id}" method="post" style="display:inline-block;">
                                <button type="submit" class="btn btn-sm btn-danger">Reject</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jspf" %>