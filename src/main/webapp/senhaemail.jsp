<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<title>Recuperação de senha</title>
<style type="text/css">
form {
	position: absolute;
	top: 40%;
	left: 33%;
	right: 33%;
}
.fail{
position: absolute;
	top: 60%;
	left: 33%;
	right: 33%;
	color: red;
}
.success{
position: absolute;
	top: 60%;
	left: 33%;
	right: 33%;
	color: green;
}
</style>
</head>
<body>
	<form action="ServletRecuperarSenha" method="post">
		<div class="mb-3">
			<label class="form-label">Digite o e-mail:</label> <input
				class="form-control" type="email" name="recuperaemail"
				id="recuperaemail" required style="width:400px">
		</div>
		<button type="submit" class="btn btn-primary" style="width:400px">Enviar</button>
	</form>
	<h6 class="fail">${msg}</h6>
	<h6 class="success">${msgSuccess}</h6>
	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
</body>
</html>