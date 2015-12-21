<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page language="java" import="org.json.JSONObject"%>
<%@page language="java" import="org.json.JSONTokener"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="Training Management System">
<meta name="author" content="Muneer">

<title>Admin Dashboard</title>

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
	
	<script type="text/javascript">
	function loadDashboard(){
		$("#approveTraining").hide();
		$("#profilePage").hide();
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
	
	function approveTraining(btn){
		var tID = btn.value;
		swal({
			title: "Are you sure?",   
			text: "The training session will be approved!",   
			type: "info",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, Approve!",   
			closeOnConfirm: false }, 
			function(){   
				$.ajax({			
					url: "/TrainingManagement/TMSServlet",
					method: "POST",
					data: {
						empID : "<%=ID%>",
						trainingID : tID,
						hiddenValue : "9"
					},
					success:function( data ){
						window.location.reload();
					}
				});
			});
	}
	
	function approveTrainingPage(){
		$("#profilePage").hide();
		$("#dashboard").hide();
		$('#about').hide();
		$("#approveTraining").show();
		$.ajax({			
			url: "/TrainingManagement/TMSServlet",
			method: "POST",
			data: {
				empID : "<%=ID%>",
				hiddenValue : "8"
			},
			success:function( data ){		
				$('#approveTraining').replaceWith($(data).find('#approveTraining').show());
			}
		});
	}
	
	function loadProfile(){
		$("#dashboard").hide();
		$("#approveTraining").hide();
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
	
	function rejectTraining(btn){
		var tID = btn.value;
		swal({
			title: "Are you sure?",   
			text: "The training session will be deleted!",   
			type: "warning",   
			showCancelButton: true,   
			confirmButtonColor: "#DD6B55",   
			confirmButtonText: "Yes, Delete!",   
			closeOnConfirm: false }, 
			function(){   
				$.ajax({			
					url: "/TrainingManagement/TMSServlet",
					method: "POST",
					data: {
						empID : "<%=ID%>",
						trainingID : tID,
						hiddenValue : "10"
					},
					success:function( data ){
						window.location.reload();
					}
				});
			});
	}
	
	function aboutPage(){
		$("#dashboard").hide();
		$("#approveTraining").hide();
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
					<li class="active"><a href="#" onClick="approveTrainingPage()">Approve Training Session
					<span class="sr-only">(current)</span>
					</a></li>
				</ul>
				<ul class="nav nav-sidebar">
					<li><a href="index.jsp" onClick="logout()">Logout</a></li>
				</ul>
			</div>
			<div class="col-sm-9" id="profilePage" style="display: none;">
				<h2 class="sub-header">Admin Details</h2>
				<div class="container">
					<c:forEach items="${adminDetails}" var="adminInfo">
						<h4>
							<b>Name&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${adminInfo.getName()}"></c:out>
						</h4>
						<h4>
							<b>Contact No&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${adminInfo.getContactNo()}"></c:out>
						</h4>
						<h4>
							<b>Email ID&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${adminInfo.getEmailID()}"></c:out>
						</h4>
						<h4>
							<b>Address&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${adminInfo.getAddress()}"></c:out>
						</h4>
						<h4>
							<b>Age&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${adminInfo.getAge()}"></c:out>
						</h4>
						<h4>
							<b>Department&#09;&#09;&#09;:</b>&nbsp;
							<c:out value="${adminInfo.getDepartment()}"></c:out>
						</h4>
					</c:forEach>
				</div>
			</div>

			<div class="col-sm-9" id="approveTraining" style="display: none;">
				<h2 class="sub-header">List of Trainings pending approval</h2>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Training ID</th>
								<th>Training Name</th>
								<th>TrainingType</th>
								<th>Approve</th>
								<th>Reject</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${pendingTrainingList}" var="trainingList">
								<tr>
									<td><c:out value="${trainingList.getTrainingID()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingName()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingType()}"></c:out></td>
									<td><button class="btn btn-sm btn-success"
											value="${trainingList.getTrainingID()}"
											onClick="javascript:approveTraining(this);">Approve</button></td>
									<td><button class="btn btn-sm btn-danger"
											value="${trainingList.getTrainingID()}"
											onClick="javascript:rejectTraining(this);">Reject</button></td>
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

				<h2 class="sub-header">List of All Trainings</h2>
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>Training ID</th>
								<th>Training Name</th>
								<th>TrainingType</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${allTrainingList}" var="trainingList">
								<tr>
									<td><c:out value="${trainingList.getTrainingID()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingName()}"></c:out></td>
									<td><c:out value="${trainingList.getTrainingType()}"></c:out></td>
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
		src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

</body>
</html>