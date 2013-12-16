<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    $(".circular").ready(function() {
    <sec:authorize access="hasRole('ROLE_SOCIAL_USER')">
        $(".circular").css("background-image", "url(http://graph.facebook.com/<sec:authentication property='principal.username' />/picture)");
    </sec:authorize>
        });
</script>

<nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li id ="teams"><a href="/teams/">Home</a></li>
            <li id="libraries"><a href="/libraries/">Libraries</a></li> 
        </ul>

        <sec:authorize access="isAuthenticated()">
            <div class='circular'> </div>
            <ul class="nav navbar-nav navbar-right" style="margin-right: 60px;">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><sec:authentication property="principal.username" /> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value='/j_spring_security_logout' />">Log out</a></li>
                    </ul>
                </li>
            </ul>
        </sec:authorize>
    </div>
</nav>
