
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    $(".circular").ready(function() {
    <sec:authorize access="hasRole('ROLE_SOCIAL')">
        var image = "<sec:authentication property='principal.password' />";
        image = $('<div/>').html(image).text();
        $(".circular").css("background-image", "url("+image+")");
    </sec:authorize>
        });
</script>

<nav class="navbar navbar-default navbar-static-top navbar-inverse" role="navigation">
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <ul class="nav navbar-nav">
            <li id ="teams"><a href="/teams/">Home</a></li>
            <li id="libraries"><a href="/libraries/">Libraries</a></li> 
            <sec:authorize access="hasRole('ROLE_ADMIN')">
		        <li class="dropdown" >
		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Admin<b class="caret"></b></a>
		          <ul class="dropdown-menu">
		            <li><a href="/admin/enable-users">Enable users</a></li>
		          </ul>
		        </li>
		    </sec:authorize>
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
