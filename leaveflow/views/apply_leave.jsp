<%@ include file="header.jspf" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-dark text-white">
                <h4>Apply for Leave</h4>
            </div>
            <div class="card-body">
                <%-- 
                  This form uses Spring's <form:form> tag.
                  It binds to the 'leaveRequest' object we passed from the controller.
                --%>
                <form:form modelAttribute="leaveRequest" action="${pageContext.request.contextPath}/apply" method="post">
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label for="startDate" class="form-label">Start Date</label>
                            <form:input path="startDate" type="date" cssClass="form-control" required="true" />
                        </div>
                        <div class="col-md-6 mb-3">
                            <label for="endDate" class="form-label">End Date</label>
                            <form:input path="endDate" type="date" cssClass="form-control" required="true" />
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="language" class="form-label">Language of Reason</label>
                        <%-- This dropdown binds to the 'language' field --%>
                        <form:select path="language" cssClass="form-select" required="true">
                            <form:option value="en">English</form:option>
                            <form:option value="hi">Hindi</form:option>
                            <form:option value="ta">Tamil</form:option>
                        </form:select>
                    </div>

                    <div class="mb-3">
                        <label for="reason" class="form-label">Reason</label>
                        <%-- This textarea binds to the 'reason' field --%>
                        <form:textarea path="reason" cssClass="form-control" rows="4" required="true" />
                    </div>

                    <button type="submit" class="btn btn-success w-100">Submit Request</button>
                </form:form>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jspf" %>