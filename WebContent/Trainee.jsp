<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" import="org.json.JSONObject"%>
<%@page language="java" import="org.json.JSONTokener"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="Training Management System">
<meta name="author" content="Muneer">

<title>Trainee Dashboard</title>

<!-- Bootstrap core CSS -->
<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="jumbotron.css" rel="stylesheet">

</head>

<body>

	<%
		String str = request.getAttribute("LoginData").toString();
		JSONObject json = (JSONObject) new JSONTokener(str).nextValue();
		String ID = json.getString("ID");
		//String userType = json.getString("Type_of_User");
	%>

	<script>
	function loadDashboard(){
		$("#profilePage").hide();
		$("#createTraining").hide();
		$("#findTraining").hide();
		$('#about').hide();
		$("#dashboard").show();
		$.ajax({			
			url: "/TrainingManagement/TMSServlet",
			method: "POST",
			data: {
				empID : "<%=ID%>",
				hiddenValue : "2"
			},
			success:function( data ){		
				$('#dashboard').replaceWith($(data).find('#dashboard').show());
			}
		});
	}
	
	function withDrawTraining(btn){
		var tID = btn.value;
		swal({
			title: "Are you sure?",   
			text: "You will be withdrawn from the training!",   
			type: "warning",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, Withdraw!",   
			closeOnConfirm: false }, 
			function(){   
				$.ajax({			
					url: "/TrainingManagement/TMSServlet",
					method: "POST",
					data: {
						empID : "<%=ID%>",
						trainingID : tID,
						hiddenValue : "4"
					},
					success:function( data ){	
						window.location.reload();
					}
				}); 
			});
	}
	
	function createButtonClick(){
		loadDashboard();
	}
	
	function enrollTraining(btn){
		var tID = btn.value;
		swal({
			title: "Are you sure?",   
			text: "You will be enrolled to the training!",   
			type: "info",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, Enroll!",   
			closeOnConfirm: false }, 
			function(){   
				$.ajax({			
					url: "/TrainingManagement/TMSServlet",
					method: "POST",
					data: {
						empID : "<%=ID%>",
						trainingID : tID,
						hiddenValue : "6"
					},
					success:function( data ){	
						window.location.reload();
					}
				});
			});
	}
	
	function createTraining(){
		$("#dashboard").hide();
		$("#profilePage").hide();
		$("#findTraining").hide();
		$('#about').hide();
		$("#createTraining").show();
	}
	
	function findTraining(){
		$("#dashboard").hide();
		$("#profilePage").hide();
		$("#createTraining").hide();
		$('#about').hide();
		$("#findTraining").show();
		$.ajax({			
			url: "/TrainingManagement/TMSServlet",
			method: "POST",
			data: {
				empID : "<%=ID%>",
				hiddenValue : "5"
			},
			success:function( data ){
				$('#findTraining').replaceWith($(data).find('#findTraining').show());
			}
		});	
	}
	
	function loadProfile(){
		$("#dashboard").hide();
		$("#findTraining").hide();
		$("#createTraining").hide();
		$('#about').hide();
		$("#profilePage").show();
		$.ajax({			
			url: "/TrainingManagement/TMSServlet",
			method: "POST",
			data: {
				empID : "<%=ID%>",
					hiddenValue : "3"
			},
			success : function(data) {
				$('#profilePage').replaceWith(
						$(data).find('#profilePage').show());
			}
		});
	}
	
	function aboutPage(){
		$("#dashboard").hide();
		$("#findTraining").hide();
		$("#createTraining").hide();
		$("#profilePage").hide();
		$('#about').show();
	}
	
	function logout(){
		$.ajax({			
			url: "/TrainingManagement/TMSServlet",
			method: "POST",
			data: {
				empID : "<%=ID%>",
				hiddenValue : "11"
			},
			success : function(data) {
			}
		});
	}
	
	</script>

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Training Management System</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#" onClick="loadDashboard()">Dashboard</a></li>
				<li><a href="#" onClick="loadProfile()">Profile</a></li>
				<li><a href="#" onClick="aboutPage()">About</a></li>
			</ul>
		</div>
	</div>
	</nav>

	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<li class="active"><a href="#" onClick="createTraining()">Create
							New Training<span class="sr-only">(current)</span>
					</a></li>
					<li class="active"><a href="#" onClick="findTraining()">Find
							Training Session<span class="sr-only">(current)</span>
					</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="index.jsp" onClick="logout()">Logout</a></li>
				</ul>
			</div>
			<div class="col-sm-9" id="profilePage" style="display: none;">
				<h2 class="sub-header">Trainee Details</h2>
				<div class="container">
					<c:forEach items="${traineeDetails}" var="traineeInfo">
						<h4>
							<b>Name&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${traineeInfo.getName()}"></c:out>
						</h4>
						<h4>
							<b>Contact No&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${traineeInfo.getContactNo()}"></c:out>
						</h4>
						<h4>
							<b>Email ID&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${traineeInfo.getEmailID()}"></c:out>
						</h4>
						<h4>
							<b>Address&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${traineeInfo.getAddress()}"></c:out>
						</h4>
						<h4>
							<b>Age&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${traineeInfo.getAge()}"></c:out>
						</h4>
						<h4>
							<b>Department&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${traineeInfo.getDepartment()}"></c:out>
						</h4>
					</c:forEach>
				</div>
			</div>

			<div class="col-sm-9" id="createTraining" style="display: none;">
				<form class="createTrainingForm" action="TMSServlet" method="POST">
					<fieldset>
						<legend>Create New Training</legend>
						<input type="hidden" name="hiddenValue" value="7"> <input
							type="hidden" name="empID" value="<%=ID%>">
						<div class="form-group">
							<label class="col-md-4 control-label" for="ID">Training
								ID</label>
							<div class="col-md-4">
								<input id="TrainingID" name="TrainingID" type="text"
									placeholder="Enter an ID for your training"
									class="form-control input-md">
							</div>
						</div>
						<br />
						<div class="form-group">
							<label class="col-md-4 control-label" for="Category">Training
								Category</label>
							<div class="col-md-4">
								<input id="Category" name="Category" type="text"
									placeholder="Make search easier. Give it a category"
									class="form-control input-md">
							</div>
						</div>
						<br />
						<div class="form-group">
							<label class="col-md-4 control-label" for="Title">Training
								Title</label>
							<div class="col-md-4">
								<input id="Title" name="Title" type="text"
									placeholder="Your training isnt gonna name itself "
									class="form-control input-md">
							</div>
						</div>
						<br />
						<div class="form-group">
							<label class="col-md-4 control-label" for="Hours">Training
								Hours</label>
							<div class="col-md-4">
								<input id="Hours" name="Hours" type="text"
									placeholder="Be sensible" class="form-control input-md">
							</div>
						</div>
						<br />
						<div class="form-group">
							<label class="col-md-4 control-label" for="Strength">Training
								Strength</label>
							<div class="col-md-4">
								<input id="Strength" name="Strength" type="text"
									placeholder="Go with 10. Max 20." class="form-control input-md">
							</div>
						</div>
						<br /><br />
						<div class="form-group">
							<label class="col-md-4 control-label" for="singlebutton"></label>
							<div class="col-md-4">
								<button id="singlebutton" name="singlebutton"
									class="btn btn-primary" type="submit"
									onClick="javascript:createButtonClick();">Create New
									Training</button>
							</div>
						</div>
					</fieldset>
				</form>

			</div>

			<div class="col-sm-9" id="findTraining" style="display: none;">
				<h2 class="sub-header">List of Available Trainings</h2>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Training ID</th>
								<th>Training Name</th>
								<th>TrainingType</th>
								<th>Enroll</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${availableTrainingList}" var="trainingList">
								<tr>
									<td><c:out value="${trainingList.getTrainingID()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingName()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingType()}"></c:out></td>
									<td><button class="btn btn-sm btn-primary"
											value="${trainingList.getTrainingID()}"
											onClick="javascript:enrollTraining(this);">Enroll?</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>

			<div class="col-sm-9" id="about" style="display: none;">
				<h2 class="sub-header">About</h2>
				
				<h4><b>Group project for CS6359 created by</b></h4>
				<ul>
					<li>Hamid Muneerulhudhakalvathi
					<li>Gowtham UmaJaganathan
					<li>Himanshu Gupta
					<li>Rajshekar Jetty
					<li>Sarvotam Pal Singh
				</ul>
			</div>

			<div class="col-sm-9" id="dashboard">

				<h2 class="sub-header">List of Enrolled Trainings</h2>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Training ID</th>
								<th>Training Name</th>
								<th>TrainingType</th>
								<th>Withdraw</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${enrolledTrainingList}" var="trainingList">
								<tr>
									<td><c:out value="${trainingList.getTrainingID()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingName()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingType()}"></c:out></td>
									<td><button class="btn btn-sm btn-primary"
											value="${trainingList.getTrainingID()}"
											onClick="javascript:withDrawTraining(this);">Withdraw?</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
</body>
</html>


