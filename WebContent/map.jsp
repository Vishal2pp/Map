<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Olabs Teachers Training Status (CBSE)</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="map.css">
<link href="Libraries/ammap/ammap.css" rel="stylesheet" />
<link rel="stylesheet" href="Libraries/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="Libraries/bootstrap/css/bootstrap-responsive.css">
<link rel="icon" type="image/png" href="images/india-159941_960_720.png">
</head>
<body>
	<div class="pull-left"
		style="padding-left: 0px; margin-top: 0px; border-left-width: 0px; margin-left: 40px;">
		<ul class="theme-switcher" id="theme">
			<li class="theme-text" id="theme-text"><b>Select a theme:</b></li>
			<li class="theme-button theme-button-none"><a href="#"
				onclick="createChart('none');" class="trans-all" data-theme="none"
				title="Default" aria-label="Switch to theme none" id="default">Default</a></li>
			<li class="theme-button theme-button-light"><a href="#"
				onclick="createChart('light');" class="trans-all" data-theme="light"
				title="Light" aria-label="Switch to theme light" id="light">Light</a></li>
			<li class="theme-button theme-button-dark"><a href="#"
				onclick="createChart('dark');" class="trans-all" data-theme="dark"
				title="Dark" aria-label="Switch to theme dark" id="dark">Dark</a></li>
			<li class="theme-button theme-button-black"><a href="#"
				onclick="createChart('black');" class="trans-all" data-theme="black"
				title="Black" aria-label="Switch to theme black" id="black">Black</a></li>
			<li class="theme-button theme-button-chalk"><a href="#"
				onclick="createChart('chalk');" class="trans-all" data-theme="chalk"
				title="Chalk" aria-label="Switch to theme chalk" id="chalk">Chalk</a></li>
			<li class="icon" id="icon-text" onclick="Responsive()"><span
				class="glyphicon glyphicon-menu-hamburger"></span></li>
		</ul>
	</div>
	<!-- <div class="uploadAndInfo">

		<div class="round2">
			<table style="width: 100%">
				<tr>
					<td><b>Total CBSE Schools:</b> <span id="TotalCBSESchools"></span>
					</td>

				</tr>
				<tr>
					<td><b>Total CBSE Schools Trained:</b> <span
						id="Total_CBSESchoolsTrained"></span></td>

				</tr>

				<tr>
					<td><b>Total CBSE School Teachers Trained:</b> <span
						id="Total_CBSESchoolTeachersTrained"></span></td>

				</tr>
			</table>
		</div>
	</div> -->
	<div class="uploadAndInfo">
		<div id="statistics" class="round2 ">
			<table  border="1" >
			<col width="100">
			<col width="100">
			<col width="100">
			<col width="100">
				<tr>
					<td></td>
					<td><b>CBSE</b></td>
					<td><b>State board</b></td>
					
					<td><b>Total  </b></td>

				</tr>
				
				<tr>
					<td><b>Schools Trained</b></td>
					<td align="center"> <span id="TotalCBSESchools"></span></td>
					
					<td><span id="TotalStateSchools"></span></td>
					<td><span id="TotalSchools"></span></td>
				</tr>
				
				<tr>
					<td><b>Teachers Trained</b> </td>
					<td><span id="Total_CBSESchoolTeachersTrained"></span></td>
					<td><span id="Total_StateSchoolTeachersTrained"></span></td>
					<td><span id="TotalTeachers"></span></td>
				</tr>
			</table>
		</div>
	</div>
	<div id="chartdiv"></div>
	<script src="Libraries/jquery.min.js"></script>
	<script src="Libraries/bootstrap/js/bootstrap.min.js"></script>
	<script src="Libraries/ammap/ammap.js"></script>
	<script src="Libraries/ammap/themes/light.js"></script>
	<script src="Libraries/ammap/themes/chalk.js"></script>
	<script src="Libraries/ammap/themes/dark.js"></script>
	<script src="Libraries/ammap/themes/black.js"></script>
	<script src="MapGeneratorScript.js"></script>
</body>
</html>

