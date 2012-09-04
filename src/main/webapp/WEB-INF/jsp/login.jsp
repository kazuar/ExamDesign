<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href="resources/css/jquery-ui/custom-theme/jquery-ui-1.8.18.custom.css"/>
	<link rel="stylesheet" type="text/css" media="screen" href="resources/css/login.css"/>
	<title>Login</title>
</head>

<body>	
	<form class="login-form" action="j_spring_security_check" method="post" >
		<fieldset>
			<!-- <legend>Login</legend>  -->

			<p>
			<label for="j_username">Username</label>:
			<input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
			</p>

			<p>
			<label for="j_password">Password</label>:
			<input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
			</p>

			<p><input type="submit" value="Login"/></p>
		</fieldset>
	</form>
	<c:choose>
		<c:when test="${messageType=='error'}">
			<div class="ui-widget">
				<div class="ui-state-error ui-corner-all" style='margin-top: 20px; padding: 0 .7em; font-size: 15px;'>
					<span class="ui-icon ui-icon-alert" style="float: left; margin-right: .3em;"></span>
					${message}
				</div>
			</div>
		</c:when>
		<c:when test="${messageType=='information'}">
			<div class="ui-widget">
				<div class="ui-state-highlight ui-corner-all" style='margin-top: 20px; padding: 0 .7em; font-size: 15px;'>
					<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
					${message}
				</div>
			</div>
		</c:when>
	</c:choose>
</body>
</html>