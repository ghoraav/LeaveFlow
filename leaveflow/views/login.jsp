<%@ include file="header.jspf" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card">
            <div class="card-header bg-primary text-white">
                <h4><spring:message code="login.welcome" /></h4>
            </div>
            <div class="card-body">
                <form action="${pageContext.request.contextPath}/login" method="post">
                    
                    <%-- Show login error, if any --%>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger"><spring:message code="login.error" /></div>
                    </c:if>
                    
                    <div class="mb-3">
                        <label for="username" class="form-label"><spring:message code="login.username" /></label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label"><spring:message code="login.password" /></label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary w-100"><spring:message code="login.button" /></button>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jspf" %>