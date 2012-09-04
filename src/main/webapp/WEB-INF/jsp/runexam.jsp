<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<html>
<head>
	
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/global.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style.css"/>'/>
	
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.8.16.custom.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/slides.min.jquery.js"/>'></script>
	
	<!-- <title>Run Exam</title>  -->
	
	<script type='text/javascript'>
	$(function() {
		$("#slides").slides();
		
		// Hide all correct answers
		$("div[id^=openCorrectAnswer]").each(function() {
			$(this).hide();
		});
		
		$("input[id^=showFullAnswer]").each(function() {
			$(this).click(function() {
				$(this).parent().next().show();
			});
		});
		
		$("input[id^=showCorrectAnswer]").each(function() {
			$(this).click(function() {
				//$(this).parent().find("input[id^=CorrectAnswer]").parent().effect("highlight", {}, 3000);
				$(this).parent().find("input[id^=CorrectAnswer]").parent().css("background", "#599100");
				$(this).parent().find("input[id^=CorrectAnswer]").parent().css("color", "white");
			});
		});
	});
	</script>
</head>

<body id="runExamBody">
	<h1 id='banner'>Run Exam: ${exam.examName}</h1>
	
	<div id="container">
		<div id="example">
			<div id="slides">
				<div class="slides_container">
					<c:forEach items="${exam.questions}" var="question" varStatus="row">
						<div class="slide">
							<h1>${question.questionText}</h1>
							<c:if test="${fn:length(question.answers) > 1}">
								<c:forEach items="${question.answers}" var="questionAnswers">
									<div>
										<input type="checkbox" />${questionAnswers.answer.answerText}
										<c:if test="${questionAnswers.isCorrect}">
											<input type="hidden" id="CorrectAnswer${row.index}"/>
										</c:if>
									</div>
								</c:forEach>
								<input type="button" id="showCorrectAnswer${row.index}" value="Show Answer"/>
							</c:if>
							<c:if test="${fn:length(question.answers) == 1}">
								<p><textarea></textarea></p>
								<p>
								<input type="button" id="showFullAnswer${row.index}" value="Show Answer"/>
								</p>
								<div id="openCorrectAnswer${row.index}">
									<p>${question.answers[0].answer.answerText}</p>
								</div>
							</c:if>
						</div>
					</c:forEach>
				</div>
				<a href="#" class="prev"><img src="../resources/img/arrow-prev.png" width="24" height="43" alt="Arrow Prev"></a>
				<a href="#" class="next"><img src="../resources/img/arrow-next.png" width="24" height="43" alt="Arrow Next"></a>
			</div>
			<img src="../resources/img/example-frame.png" width="739" height="341" alt="Example Frame" id="frame">
		</div>
	</div>
</body>
</html>