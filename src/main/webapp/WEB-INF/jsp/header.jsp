<%@include file="taglib_includes.jsp" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<c:url value="logout" var="logoutUrl"/>

<html lang="en">
	<head>
	
		<link rel="stylesheet" type="text/css" media="screen" href="/ExamDesign/resources/css/jquery-ui/custom-theme/jquery-ui-1.8.18.custom.css"/>
		<link rel="stylesheet" type="text/css" media="screen" href="/ExamDesign/resources/css/superfish.css"/>
		
		<script type='text/javascript' src="/ExamDesign/resources/js/jquery-1.6.4.min.js"/></script>
		<script type='text/javascript' src="/ExamDesign/resources/js/jquery-ui-1.8.16.custom.min.js"/></script>
		<script type="text/javascript" src="/ExamDesign/resources/js/hoverIntent.js"/></script>
		<script type="text/javascript" src="/ExamDesign/resources/js/superfish.js"/></script>
		<script type="text/javascript" src="/ExamDesign/resources/js/jquery.easing.1.3.js"/></script>
		
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		
		<script type="text/javascript">

		// initialise plugins
		jQuery(function(){
			jQuery('ul.sf-menu').superfish();
		});

		</script>
	</head>
	<body>
		<ul class="sf-menu">
			<li class="current">
				<a href="#a">Exams</a>
				<ul>
					<li>
						<a href="/ExamDesign/exams">List Exams</a>
					</li>
					<li>
						<a href="/ExamDesign/exams/add">Create Exam</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="#">Questions</a>
				<ul>
					<li>
						<a href="/ExamDesign/questions">List Questions</a>
					</li>
					<li>
						<a href="/ExamDesign/questions/add">Create Question</a>
					</li>
					<li>
						<a href="/ExamDesign/categories">Question Categories</a>
					</li>
					<li>
						<a href="/ExamDesign/difficulties">Question Difficulties</a>
					</li>
					<li>
						<a href="/ExamDesign/questiontypes">Question Types</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="#">Answers</a>
				<ul>
					<li>
						<a href="/ExamDesign/answers">List Answers</a>
					</li>
				</ul>
			</li>
			<sec:authorize access="hasRole('ROLE_ADMIN')">
			<li>
				<a href="#">User Management</a>
				<ul>
					<li>
						<a href="/ExamDesign/users">Users</a>
					</li>
				</ul>
			</li>
			</sec:authorize>
			<li>
				<a href="${logoutUrl}">Logout</a>
			</li>	
		</ul>
	</body>
</html>