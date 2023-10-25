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
															<button class="btn btn-primary waves-effect waves-light"
																onclick="limparForm()">Novo</button>
															<button class="btn btn-success waves-effect waves-light">Salvar</button>
															<button class="btn btn-info waves-effect waves-light"
																onclick="deleteUsuarioAjax()">Excluir</button>
															<button type="button" class="btn btn-warning"
																data-toggle="modal" data-target="#buscarNomeModal">Pesquisar</button>
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
	
	function limparForm() {
		var elementos = document.getElementById("form").elements;
		for (pi = 0; pi < elementos.length; pi++) {
			elementos[pi].value = '';
		}
	}

	function deleteUsuarioAjax() {
		if (confirm('Deseja deletar o usuário?')) {
			var urlAction = document.getElementById("form").action; //busca os valores da servlet 
			var idUser = document.getElementById("id").value;
			$.ajax({
				method : "get",
				url : urlAction,
				data : "id=" + idUser + "&acao=deletarAjax",
				success : function(response) {
					document.getElementById("msg").textContent = response;
					limparForm();
				}
			}).fail(
					function(xhr, status, errorThrow) {
						alert("Erro ao deletar usuário por id: "
								+ xhr.responseText);
					});
		}
	}
	function deleteUsuario() {
		if (confirm('Deseja deletar o usuário?')) {
			document.getElementById("form").method = 'get';
			document.getElementById("acao").value = 'deletar';
			document.getElementById("form").submit();
		}
	}
		function buscarNomeAjax(){
			var buscaNome = document.getElementById("buscaNome").value;
			if (buscaNome != null && buscaNome != '' && buscaNome.trim()!=''){
				var urlAction = document.getElementById("form").action;
				$.ajax({
					method : "get",
					url : urlAction,
					data : "buscaNome=" + buscaNome + "&acao=buscarNomeAjax",
					success : function(response) {
					var json = JSON.parse(response); //variavel que recebe a minha resposta e converte para json
					$('#tabelaresultados > tbody > tr').remove();
					for(var p = 0; p<json.length; p++){
						$('#tabelaresultados > tbody').append
						('<tr><td>'+json[p].id+'</td><td>'+json[p].nome+'</td><td><button type="button" class="btn btn-info" onclick="verEditar('
								+json[p].id+')">Ver</button></td></tr>');
					}
					document.getElementById('totalresultados').textContent = 'Resultados: '+json.length;
					}
				}).fail(
						function(xhr, status, errorThrow) {
							alert("Erro ao buscar usuário pelo nome: "
									+ xhr.responseText);
						})
			}
		}
		function verEditar(id){
			var urlAction = document.getElementById("form").action;
			window.location.href = urlAction + '?acao=buscarEditar&id='+id; 	/*redireciona para uma nova url cocatenando os valores da 
																				acao e id*/
		}
	</script>

	<!-- Modal -->
	<div class="modal fade" id="buscarNomeModal" tabindex="-1"
		role="dialog" aria-labelledby="buscarNomeModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Pesquisar
						usuário</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="input-group mb-3">
						<input type="text" class="form-control" placeholder="Nome"
							aria-label="nome" aria-describedby="basic-addon2" id="buscaNome">
						<div class="input-group-append">
							<button onclick="buscarNomeAjax()" class="btn btn-success" type="button">Buscar</button>
						</div>
					</div>
					<div style="height: 300px; overflow: scroll;">
						<table class="table" id="tabelaresultados">
							<thead>
								<tr>
									<th scope="col">ID</th>
									<th scope="col">Nome</th>
									<th scope="col">Ver</th>
								</tr>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
					<span id="totalresultados"></span>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">Fechar</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
