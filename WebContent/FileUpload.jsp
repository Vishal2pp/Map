<%
	response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
   	response.addHeader("Pragma", "no-cache"); 
   	response.addDateHeader ("Expires", 0);
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!Doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="Libraries/bootstrap/css/bootstrap.min.css">
<title>File Upload</title>
<link rel="icon" type="image/png" href="images/512.png">
</head>


<%
    if((session.getAttribute("u_name"))!=null && (session.getAttribute("u_password")!=null))
    {
%>
<body>
	
	<div class="container" style="padding-top: 15px">
		<div class="panel panel-primary" style="height: 434px;">
		
			<div class="panel-heading">OLABS WORKSHOP</div>
			<div class="panel-body">
			<h3 style="margin-left: 25px" >Welcome- <%=session.getAttribute("u_name")%><a style="float: Right" href="<%=request.getContextPath()%>/logout">Logout</a> </h3>
				<div class="panel panel-body">
					<h2 style="padding-left: 352px; height: 64px;">ICT Workshop
						File Upload Page</h2>
					<h2 style="margin-left: 25px">Choose File to Upload</h2>
					<input type="button" class="btn btn-primary" value="Show Map"
						style="float: right;"
						onclick="location.href='${pageContext.request.contextPath}/map.jsp'">
						
						<%-- <input type="button" class="btn btn-primary" value="Show State Map"
						style="float: right;"
						onclick="location.href='${pageContext.request.contextPath}/stateMap.jsp'"> --%>

					<ul>
						<li><h5>The File must be a valid Excel file with
								extension .xlsx</h5></li>
						<li><h5>The size of file must not be greater than 10mb.</h5></li>
						<li><h5>The File must contain Statistical data of ICT
								Workshop.</h5></li>
						<li><h5>The File must be in desired format.</h5></li>
					</ul>


					<c:set var="status" value="${sessionScope['status']}" />
					<c:choose>
						<c:when test='${status eq "successful"}'>
							<c:set var="fontColor" value="green" />
						</c:when>
						<c:otherwise>
							<c:set var="fontColor" value="red" />
						</c:otherwise>
					</c:choose>
					<h4 id="#message" style="margin-left: 25px;color:${fontColor}">
						${sessionScope["message"]}
						
					</h4>

					<form action="UploadFile" method="post"
						enctype="multipart/form-data" onsubmit="return Validate(this);">
						<input type="file" name="file" id="fupload"
							style="margin-left: 28px; display: inline;"> <input
							type="submit" value="Upload" style="margin-top: 7px;">
					</form>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		function CheckSize(oForm) {
			var sFileSize = $("input[type='file']").get(0).files[0].size;
			if (sFileSize <= 10485760)
				return true;
			else
				return false;
		}
		var _validFileExtensions = [ ".xlsx" ];
		function Validate(oForm) {
			var sFileName = $("input[type='file']").val();
			if (sFileName === null || sFileName === ""
					|| sFileName === undefined) {
				document.getElementById("#message").style.color = "red";
				document.getElementById("#message").innerHTML = "Please choose a valid file.";
				return false;
			} else {
				var FileSizeValid = CheckSize(oForm);
				if (FileSizeValid) {
					var sFileType = $("#fupload").get(0).files[0].type;
					var blnValid = false;
					for (var j = 0; j < _validFileExtensions.length; j++) {
						var sCurExtension = _validFileExtensions[j];
						if (sFileName.substr(
								sFileName.length - sCurExtension.length,
								sCurExtension.length).toLowerCase() == sCurExtension
								.toLowerCase()) {
							blnValid = true;
							break;
						}

						if (!blnValid) {
							document.getElementById("#message").style.color = "red";
							document.getElementById("#message").innerHTML = "Sorry,File is invalid, allowed extension is .xlsx";
							return false;
						} else
							return true;
					}

				} else {
					document.getElementById("#message").style.color = "red";
					document.getElementById("#message").innerHTML = "Sorry, File is too big";
					return false;
				}
			}
		}
	</script>
	<script src="Libraries/jquery.min.js"></script>
	<script src="Libraries/bootstrap/js/bootstrap.min.js"></script>
</body>
  <%
    }
    else
    {
    	response.sendRedirect("index.jsp");
    
     }
   %>


</html>
