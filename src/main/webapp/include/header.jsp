<nav class="navbar navbar-light navbar-expand-md fixed-top" id="mainNav">
    <div class="container"><a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">Conf</a>
        <button data-bs-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-bs-target="#navbarResponsive"
                type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"
                value="Menu"><i class="fa fa-bars"></i></button>
        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item nav-link"><a class="nav-link active" href="#">About</a></li>
                <li class="nav-item nav-link"><a class="nav-link active"
                                                 href="${pageContext.request.contextPath}/eventList">Events</a>
                </li>
            </ul>
            <a href="${pageContext.request.contextPath}/registration">SIGN IN</a><span class="text-white navbar-text">&nbsp;/&nbsp;</span>
            <a href="${pageContext.request.contextPath}/login">LOG IN</a>
        </div>
    </div>
</nav>
