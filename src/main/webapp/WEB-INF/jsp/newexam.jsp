<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url value="/questions/records" var="recordsUrl"/>
<c:url value="/questions/create" var="addUrl"/>
<c:url value="/questions/update" var="editUrl"/>
<c:url value="/questions/delete" var="deleteUrl"/>

<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/jquery-ui/custom-theme/jquery-ui-1.8.18.custom.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/ui.jqgrid-4.3.1.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/resources/css/style.css"/>'/>
	
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-ui-1.8.16.custom.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/grid.locale-en-4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery.jqGrid.min.4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom.js"/>'></script>
	
	<title>New Exam</title>
	
	<script type='text/javascript'>
	$(function() {
		/*
		$("#addExistingQuestion").click(function() {
			var rowid = $("#grid").getGridParam('selrow');
			var answerText = $("#grid").jqGrid('getCell', rowid, 2);
			
			// we have the response
	        var answersCount = $("#answersTable tr").length;
	        var newQuestion = '<tr><td><input type="hidden" id="answers' + answersCount + '.answer.id" name="answers[' + answersCount + '].answer.id" value="' + rowid + '">' + (answersCount + 1) + ') ' + answerText + '</td></tr>';
	        
	        if (answersCount > 0) {
	        	$('#answersTable tr:last').after(newQuestion);
	        } else {
	        	$('#answersTable > tbody:last').after(newQuestion);
	        }
		});
	
		$("#addNewQuestion").click(function() {
			
			// get the form values
			var newQuestionText = $('#newQuestionText').val();

			$.ajax({
		        type: "POST",
		        url: "/TestsDesign/answer/createAndAddToQuestion",
		        data: "text=" + newQuestionText,
		        success: function(response) {
		        	
		            // we have the response
		            var answersCount = $("#answersTable tr").length;
		            var newQuestion = '<tr><td><input type="hidden" id="answers' + answersCount + '.answer.id" name="answers[' + answersCount + '].answer.id" value="' + response.id + '">' + (answersCount + 1) + ') ' + response.text + '</td></tr>';
		            
		            if (answersCount > 0) {
		            	$('#answersTable tr:last').after(newAnswer);
		            } else {
		            	$('#answersTable > tbody:last').after(newAnswer);
		            }
		         },
		         error: function(e){
		             alert('Error: ' + e);
		         }
		    });
		});
		*/
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['Id', 'Question', 'Category', 'Difficulty', 'Question Type'],
		   	colModel:[
		   		{name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true},
		   		{name:'questionText',index:'questionText', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}},
		   		{name:'category',index:'category', width:50, editable:true, editrules:{required:true}, 
		   			edittype:"select", formatter:'select', stype: 'select'},
		   		{name:'difficulty',index:'difficulty', width:50, editable:true, editrules:{required:true}, 
		   			edittype:"select", formatter:'select', stype: 'select'},
		   		{name:'questionType',index:'questionType', width:50, editable:true, editrules:{required:true}, 
		   			edittype:"select", formatter:'select', stype: 'select'}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
		   	height: 120,
		   	autowidth: true,
			rownumbers: true,
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Records",
		    emptyrecords: "Empty records",
		    loadonce: false,
		    beforeProcessing: function(data) {
		    	$("#grid").setColProp('category', { 
		    		editoptions:{value: data.map.categories},
		    		formatoptions:{value: data.map.categories}, 
		    		searchoptions: {sopt:['eq'], value: ":;" + data.map.categories}});
		    	$("#grid").setColProp('difficulty', { 
		    		editoptions:{value: data.map.difficulties},
		    		formatoptions:{value: data.map.difficulties}, 
		    		searchoptions: {sopt:['eq'], value: ":;" + data.map.difficulties}});
		    	$("#grid").setColProp('questionType', { 
		    		editoptions:{value: data.map.questiontypes},
		    		formatoptions:{value: data.map.questiontypes}, 
		    		searchoptions: {sopt:['eq'], value: ":;" + data.map.questiontypes}});
		    },
		    loadComplete: function() {
		    	// Toolbar Search
				$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
		    },
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "id"
		    }
		});

		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:false, del:false, search:true},
				{}, {}, {}, 
				{ 	// search
					sopt:['cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'],
					closeOnEscape: true, 
					multipleSearch: true, 
					closeAfterSearch: true
				}
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Add To Exam", 
					buttonicon:"ui-icon-plus", 
					onClickButton: addQuestionToExam,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
		$("img[id^=removeRow]").click(function() {
        	$(this).closest('tr').remove();
        	$("#questionsTable tr td:first-child").each(function(index) {
        		$(this).text((index + 1) + '.');
        	});
        	$("#questionsTable :input[id^=questions]").each(function(index) {
        		$(this).attr("id", "questions" + index + ".id");
        		$(this).attr("name", "questions[" + index + "].id");
        	});
        });
	});
	
	function addQuestionToExam() {
		// Get the currently selected row
		var row = $('#grid').jqGrid('getGridParam','selrow');
		
		if( row != null ) {
			var questionText = $("#grid").jqGrid('getCell', row, 2);
			
			// Test if answer id already exists
			var questionId = $('input[id^=questions][value="' + row + '"]');
			if (questionId.length == 0) {
				
				// we have the response
		        var questionsCount = $("#questionsTable tr").length;
		        var newQuestion = '<tr><td>' + (questionsCount + 1) + '.</td><td><input type="hidden" id="questions' + questionsCount + '.id" name="questions[' + questionsCount + '].id" value="' + row + '">' + questionText + '</td><td><IMG id="removeRow' + questionsCount + '" SRC="../resources/img/close.png" width="10" height="10"></td></tr>';
		        
		        if (questionsCount > 0) {
		        	$('#questionsTable tr:last').after(newQuestion);
		        } else {
		        	$('#questionsTable > tbody:last').after(newQuestion);
		        }
		        
		        $("img[id^=removeRow]").click(function() {
		        	$(this).closest('tr').remove();
		        	$("#questionsTable tr td:first-child").each(function(index) {
		        		$(this).text((index + 1) + '.');
		        	});
		        	$("#questionsTable :input[id^=questions]").each(function(index) {
		        		$(this).attr("id", "questions" + index + ".id");
		        		$(this).attr("name", "questions[" + index + "].id");
		        	});
		        });
			} else {
				$('#msgbox').text('Question already exists in exam!');
				$('#msgbox').dialog( 
						{	title: 'Error',
							modal: true,
							buttons: {"Ok": function()  {
								$(this).dialog("close");} 
							}
						});
			}
		} else {
			$('#msgbox').text('You must select a record first!');
			$('#msgbox').dialog( 
					{	title: 'Error',
						modal: true,
						buttons: {"Ok": function()  {
							$(this).dialog("close");} 
						}
					});
		}
	}

	function addRow() {
   		$("#grid").jqGrid('setColProp', 'answerText', {editoptions:{readonly:false, size:10}});
   		
		// Get the currently selected row
		$('#grid').jqGrid('editGridRow','new',
	    		{ 	url: '${addUrl}', 
					editData: {},
	                serializeEditData: function(data){ 
	                    data.id = 0; 
	                    return $.param(data);
	                },
				    recreateForm: true,
				    beforeShowForm: function(form) {
			            $('#pData').hide();  
			            $('#nData').hide();
			            $('#password',form).addClass('ui-widget-content').addClass('ui-corner-all');
				    },
					beforeInitData: function(form) {},
					closeAfterAdd: true,
					reloadAfterSubmit:true,
					afterSubmit : function(response, postdata) 
					{ 
				        var result = eval('(' + response.responseText + ')');
						var errors = "";
						
				        if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
				        }  else {
				        	$('#msgbox').text('Entry has been added successfully');
							$('#msgbox').dialog( 
									{	title: 'Success',
										modal: true,
										buttons: {"Ok": function()  {
											$(this).dialog("close");} 
										}
									});
		                }
				    	// only used for adding new records
				    	var newId = null;
				    	
						return [result.success, errors, newId];
					}
	    		});
	} // end of addRow


	function editRow() {
		// Get the currently selected row
		var row = $('#grid').jqGrid('getGridParam','selrow');
		
		if( row != null ) {
		
			$('#grid').jqGrid('editGridRow', row,
				{	url: '${editUrl}', 
					editData: {},
			        recreateForm: true,
			        beforeShowForm: function(form) {
			            $('#pData').hide();  
			            $('#nData').hide();
			        },
					beforeInitData: function(form) {},
					closeAfterEdit: true,
					reloadAfterSubmit:true,
					afterSubmit : function(response, postdata) 
					{ 
			            var result = eval('(' + response.responseText + ')');
						var errors = "";
						
			            if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
			            }  else {
			            	$('#msgbox').text('Entry has been edited successfully');
							$('#msgbox').dialog( 
									{	title: 'Success',
										modal: true,
										buttons: {"Ok": function()  {
											$(this).dialog("close");} 
										}
									});
		                }
				    	// only used for adding new records
				    	var newId = null;
			        	
						return [result.success, errors, newId];
					}
				});
		} else {
			$('#msgbox').text('You must select a record first!');
			$('#msgbox').dialog( 
					{	title: 'Error',
						modal: true,
						buttons: {"Ok": function()  {
							$(this).dialog("close");} 
						}
					});
		}
	}
	
	function deleteRow() {
		// Get the currently selected row
	    var row = $('#grid').jqGrid('getGridParam','selrow');

	    // A pop-up dialog will appear to confirm the selected action
		if( row != null ) 
			$('#grid').jqGrid( 'delGridRow', row,
	          	{	url:'${deleteUrl}', 
					recreateForm: true,
				    beforeShowForm: function(form) {
				    	//Change title
				        $(".delmsg").replaceWith('<span style="white-space: pre;">' +
				        		'Delete selected record?' + '</span>');
		            	//hide arrows
				        $('#pData').hide();  
				        $('#nData').hide();
				    },
	          		reloadAfterSubmit:true,
	          		closeAfterDelete: true,
	          		serializeDelData: function (postdata) {
		          	      var rowdata = $('#grid').getRowData(postdata.id);
		          	      // append postdata with any information 
		          	      return {id: postdata.id, oper: postdata.oper, username: rowdata.username};
		          	},
	          		afterSubmit : function(response, postdata) 
					{ 
			            var result = eval('(' + response.responseText + ')');
						var errors = "";
						
			            if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
			            }  else {
			            	$('#msgbox').text('Entry has been deleted successfully');
							$('#msgbox').dialog( 
									{	title: 'Success',
										modal: true,
										buttons: {"Ok": function()  {
											$(this).dialog("close");} 
										}
									});
		                }
				    	// only used for adding new records
				    	var newId = null;
			        	
						return [result.success, errors, newId];
					}
	          	});
		else {
			$('#msgbox').text('You must select a record first!');
			$('#msgbox').dialog( 
					{	title: 'Error',
						modal: true,
						buttons: {"Ok": function()  {
							$(this).dialog("close");} 
						}
					});
		}
	}
	</script>
</head>

<body>

<!-- <h1>Exam</h1>  -->

<spring:hasBindErrors name="exam">
    <c:forEach items="${errors.allErrors}" var="error">
        <spring:message message="${error}"/>
    </c:forEach>
</spring:hasBindErrors>

<form:form method="post" action="${action}Exam.html" modelAttribute="exam">
	<form:hidden path="id"/>
	
	<table border="1" width="100%">
		<tr>
			<td valign="top" width="40%">
				<table border="1" width="100%">
					<tr>
						<td><form:label path="examName">Exam Name:</form:label></td>
						<td><form:input path="examName" /></td> 
					</tr>
				</table>
			</td>
			<td>
				<table width="100%" border="1">
					<!-- 
					<tr>
						<td><h4>Questions:</h4></td>
					</tr>
					 -->
					<tr valign="top">
						<td>
							<div><strong>Questions:</strong></div>
							<table id="questionsTable" width="100%" border="1">
								<tbody>
									<c:forEach items="${exam.questions}" var="question" varStatus="row">
										<tr><td>${row.index + 1}.</td><td><form:hidden path="questions[${row.index}].id"/> ${question.questionText} </td><td><IMG id="removeRow${row.index}" SRC="../resources/img/close.png" width="10" height="10"></td></tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<input type="submit" value="Save Exam"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<div id='jqgrid'>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>
	
	<div id='msgbox' title='' style='display:none'></div>
</form:form>

</body>
</html>