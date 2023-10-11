<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"></jsp:include>
<jsp:include page="theme-loader.jsp"></jsp:include>

<body>
	<!-- Pre-loader start -->

	<!-- Pre-loader end -->
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">

			<jsp:include page="navbar.jsp"></jsp:include>
			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">
					<jsp:include page="navbarmainmenu.jsp"></jsp:include>
					<div class="pcoded-content">
						<!-- Page-header start -->
						<jsp:include page="page-header.jsp"></jsp:include>
						<!-- Page-header end -->
						<div class="pcoded-inner-content">
							<!-- Main-body start -->
							<div class="main-body">
								<div class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<div class="row">
											<div class="col-sm-12">
												<!-- Basic Form Inputs card start -->
												<div class="card">
													<div class="card-block">
														<h4>Cad. usuário</h4>
														<hr>
														<form class="form-material"
															action="<%=request.getContextPath()%>/ServletUsuarioController"
															method="post" id="form">
															<input type="hidden" name="acao" id="acao" value="">
															<div class="form-group form-default form-static-label">
																<input type="text" name="id" id="id"
																	class="form-control" required="required"
																	value="${ousuario.id}" readonly="readonly"> <span
																	class="form-bar"></span> <label class="float-label">ID:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="nome" id="nome"
																	class="form-control" required="required"
																	value="${ousuario.nome}"> <span
																	class="form-bar"></span> <label class="float-label">Nome:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="email" name="email" id="email"
																	class="form-control" required="required"
																	autocomplete="off" value="${ousuario.email}"> <span
																	class="form-bar"></span> <label class="float-label">E-mail</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="text" name="login" id="login"
																	class="form-control" required="required"
																	autocomplete="off" value="${ousuario.login}"> <span
																	class="form-bar"></span> <label class="float-label">Login:</label>
															</div>
															<div class="form-group form-default form-static-label">
																<input type="password" name="senha" id="senha"
																	class="form-control" required="required"
																	autocomplete="off" value="${ousuario.senha}"> <span
																	class="form-bar"></span> <label class="float-label">Senha:</label>
															</div>
															<button class="btn btn-primary waves-effect waves-light" onclick="limparForm()">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Salvar</button>
															<button class="btn btn-info waves-effect waves-light" onclick="deleteUsuarioAjax()">Excluir</button>
														</form>
													</div>
													<span id="msg">${msg}</span>
												</div>
											</div>
										</div>
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="javascriptfile.jsp"></jsp:include>
	<script type="text/javascript">
	function limparForm(){
		var elementos = document.getElementById("form").elements;
		for(pi=0;pi<elementos.length;pi++){
			elementos[pi].value='';
		}
	}
	
	function deleteUsuarioAjax(){
		if(confirm('Deseja deletar o usuário?')){
			var urlAction = document.getElementById("form").action; //busca os valores da servlet 
			var idUser = document.getElementById("id").value;
			$.ajax({
				method:"get",
				url: urlAction,
				data:"id="+idUser+"&acao=deletarAjax",
				success: function(response){
					document.getElementById("msg").textContent = response;
					limparForm();
				}
			}).fail (function(xhr, status, errorThrow){
				alert("Erro ao deletar usuário por id: "+xhr.responseText);
			});
		}
	}
	function deleteUsuario(){
		if(confirm('Deseja deletar o usuário?')){
		document.getElementById("form").method='get';
		document.getElementById("acao").value='deletar';
		document.getElementById("form").submit();
		}
	}
	</script>
</body>
</html>
