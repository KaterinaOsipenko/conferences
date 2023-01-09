<nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
    <div class="container"><a class="navbar-brand"
                              href="${pageContext.request.contextPath}/admin/adminHome.jsp">Conf</a>
        <button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive"
                type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"
                value="Menu"><i class="fa fa-bars"></i></button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item nav-link">
                    <a class="nav-link active" href="${pageContext.request.contextPath}/speaker/viewReports">My
                        Reports</a>
                </li>
                <li class="nav-item nav-link">
                    <a class="nav-link active"
                       href="${pageContext.request.contextPath}/speaker/profile">${user.firstName} ${user.lastName}</a>
                </li>
            </ul>
            <a href="${pageContext.request.contextPath}/logout">LOG OUT</a>
        </div>
    </div>
</nav>