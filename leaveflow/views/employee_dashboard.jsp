<%@ include file="header.jspf" %>

<h3>My Leave History</h3>

<c:choose>
    <%-- Check if the 'requests' list is empty --%>
    <c:when test="${empty requests}">
        <div class="alert alert-info">You have not submitted any leave requests yet.</div>
    </c:when>
    <c:otherwise>
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Reason</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <%-- We use <c:forEach> to loop through the list of requests --%>
                <c:forEach var="req" items="${requests}">
                    <tr>
                        <td>${req.startDate}</td>
                        <td>${req.endDate}</td>
                        <td>${req.reason}</td>
                        <td>
                            <%-- Add color to the status --%>
                            <c:choose>
                                <c:when test="${req.status == 'APPROVED'}">
                                    <span class="badge bg-success">Approved</span>
                                </c:when>
                                <c:when test="${req.status == 'REJECTED'}">
                                    <span class="badge bg-danger">Rejected</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-warning text-dark">Pending</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="footer.jspf" %>