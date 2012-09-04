<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:url value="/answers/records" var="recordsUrl"/>
<c:url value="/answers/createAndReturn" var="addUrl"/>
<c:url value="/answers/update" var="editUrl"/>
<c:url value="/answers/delete" var="deleteUrl"/>

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
	
	<title>New Question</title>
	
	<script type='text/javascript'>
	$(function() {
	
		$("#addNewAnswer").click(function() {
			
			// get the form values
			var newAnswerText = $('#newAnswerText').val();

			$.ajax({
		        type: "POST",
		        url: "/TestsDesign/answer/createAndAddToQuestion",
		        data: "text=" + newAnswerText,
		        success: function(response) {
		        	
		            // we have the response
		            var answersCount = $("#answersTable tr").length;
		            var newAnswer = '<tr><td width="5%"><input type="hidden" id="answers' + answersCount + '.answer.id" name="answers[' + answersCount + '].answer.id" value="' + response.id + '">' + (answersCount + 1) + ') ' + response.text + '</td><td width="10%"><select id="answers' + answersCount + '.isCorrect" name="answers[' + answersCount + '].isCorrect"><option value="true">true</option><option value="false" selected="selected">false</option></select></td><td><button id="rightIcon">Right Icon</button></td></tr>';
		            
		            $("#rightIcon").button({
		                icons: {
		                    primary: 'ui-icon-blank', 
		                    secondary: 'ui-icon-triangle-1-s'
		                }
		            });
		            
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
		
		$("#grid").jqGrid({
		   	url:'${recordsUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['Id', 'Answer'],
		   	colModel:[
		   		{name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true},
		   		{name:'answerText',index:'answerText', width:100, editable:true, editrules:{required:true}, editoptions:{size:10}}
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
		    caption:"Answers Repository",
		    emptyrecords: "Empty records",
		    loadonce: false,
		    loadComplete: function() {},
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
				{ 	caption:"Add Selected Answer", 
					buttonicon:"ui-icon-extlink", 
					onClickButton: addSelectedRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Create New Answer", 
					buttonicon:"ui-icon-plus", 
					onClickButton: addRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
		// Toolbar Search
		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
		
		$("img[id^=removeRow]").click(function() {
			setAnswersOrder($(this));
        });
	});
	
	function addSelectedRow() {
		
		// Get the currently selected row
		var row = $('#grid').jqGrid('getGridParam','selrow');
		
		if( row != null ) {
			var answerText = $("#grid").jqGrid('getCell', row, 2);
			
			// Test if answer id already exists
			var answerId = $('input[id^=answers][value="' + row + '"]');
			if (answerId.length == 0) {
				
				// we have the response
		        var answersCount = $("#answersTable tr").length;
		        var newAnswer = '<tr><td width="5%">' + (answersCount + 1) + '.</td><td><input type="hidden" id="answers' + answersCount + '.answer.id" name="answers[' + answersCount + '].answer.id" value="' + row + '">' + answerText + '</td><td width="10%"><select id="answers' + answersCount + '.isCorrect" name="answers[' + answersCount + '].isCorrect"><option value="true">true</option><option value="false" selected="selected">false</option></select></td><td width="5%" align="right"><IMG id="removeRow' + answersCount + '" SRC="../resources/img/close.png" width="10" height="10"></td></tr>';
		        
		        if (answersCount > 0) {
		        	$('#answersTable tr:last').after(newAnswer);
		        } else {
		        	$('#answersTable > tbody:last').after(newAnswer);
		        }
		        
		        $("img[id^=removeRow]").click(function() {
		        	setAnswersOrder($(this));
		        });
			} else {
				$('#msgbox').text('Answer already exists in question!');
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

	function setAnswersOrder(obj) {
		$(obj).closest('tr').remove();
    	$("#answersTable tr td:first-child").each(function(index) {
    		$(this).text((index + 1) + '.');
    	});
    	
    	$("#answersTable :hidden[id^=answers]").each(function(index) {
    		$(this).attr("id", "answers" + index + ".answer.id");
    		$(this).attr("name", "answers[" + index + "].answer.id");
    	});
    	$("#answersTable select[id^=answers]").each(function(index) {
    		$(this).attr("id", "answers" + index + ".isCorrect");
    		$(this).attr("name", "answers[" + index + "].isCorrect");
    	});
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
				        	
				        	// we have the response
					        var answersCount = $("#answersTable tr").length;
					        var newAnswer = '<tr><td width="5%">' + (answersCount + 1) + '.</td><td><input type="hidden" id="answers' + answersCount + '.answer.id" name="answers[' + answersCount + '].answer.id" value="' + result.message[0] + '">' + result.message[1] + '</td><td width="10%"><select id="answers' + answersCount + '.isCorrect" name="answers[' + answersCount + '].isCorrect"><option value="true">true</option><option value="false" selected="selected">false</option></select></td><td width="5%" align="right"><IMG id="removeRow' + answersCount + '" SRC="../resources/img/close.png" width="10" height="10"></td></tr>';
					        
					        if (answersCount > 0) {
					        	$('#answersTable tr:last').after(newAnswer);
					        } else {
					        	$('#answersTable > tbody:last').after(newAnswer);
					        }
					        
					        $("img[id^=removeRow]").click(function() {
					        	$(this).closest('tr').remove();
					        	$("#answersTable tr td:first-child").each(function(index) {
					        		$(this).text((index + 1) + '.');
					        	});
					        	$("#answersTable :input[id^=answers]").each(function(index) {
					        		$(this).attr("id", "answers" + index + ".answer.id");
					        		$(this).attr("name", "answers[" + index + "].answer.id");
					        	});
					        });
				        	
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
   		$("#grid").jqGrid('setColProp', 'username', {editoptions:{readonly:true, size:10}});
   		$("#grid").jqGrid('setColProp', 'password', {hidden: true});
   		$("#grid").jqGrid('setColProp', 'password', {editrules:{required:false}});
   		
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
<!-- 
<h1>Question</h1>
 -->
<spring:hasBindErrors name="question">
    <c:forEach items="${errors.allErrors}" var="error">
        <spring:message message="${error}"/>
    </c:forEach>
</spring:hasBindErrors>

<form:form method="post" action="${action}Question.html" modelAttribute="question">
	<form:hidden path="id"/>

	<table border="1" width="100%">
		<tr>
			<td><form:label path="questionText">Question:</form:label></td>
			<td>Answers:</td> 
		</tr>
		<tr>
			<td><form:textarea path="questionText" rows="7" style='width:98%; height:100%'/></td>
			<td>
				<table id="answersTable" border="1" width="100%">
					<tbody>
						<c:forEach items="${question.answers}" var="answer" varStatus="row">
							<tr>
								<td width="5%">${row.index + 1}.</td>
								<td><form:hidden path="answers[${row.index}].answer.id"/> ${answer.answer.answerText} </td>
								<td width="10%">
									<form:select path="answers[${row.index}].isCorrect">
											<form:option value="true"></form:option>
											<form:option value="false"></form:option>
									</form:select>
								</td>
								<td width="5%" align="right"><IMG id="removeRow${row.index}" SRC="../resources/img/close.png" width="10" height="10"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</td>
		</tr>
		<tr>
			<td width="40%">
				<table>
					<tr>
						<td><form:label path="category.id">Category</form:label></td>
						<td><form:select path="category.id" items="${categoryList}" itemLabel="categoryName" itemValue="id"></form:select></td>
					</tr>
					<tr>
						<td><form:label path="difficulty.id">Difficulty</form:label></td>
						<td><form:select path="difficulty.id" items="${difficultyList}" itemLabel="difficultyName" itemValue="id"></form:select></td>
					</tr>
					<tr>
						<td><form:label path="questionType.id">Question Type</form:label></td>
						<td><form:select path="questionType.id" items="${questionTypeList}" itemLabel="questionTypeName" itemValue="id"></form:select></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="Save Question"/>
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