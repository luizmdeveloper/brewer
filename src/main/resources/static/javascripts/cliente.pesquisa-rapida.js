Brewer = Brewer || {};

Brewer.PesquisaRapidaCliente = (function(){
	
	PesquisaRapidaCliente = function(){
		this.pesquisaRapidaClienteModal = $('#pesquisaRapidaCliente');
		this.nomeInput = $('#nomeClienteModal');
		this.mensagemErro = $('.js-mensagem-erro');
		this.pesquisarBtn = $('.js-pesquisa-rapida-cliente-btn');
		this.containerTabelaPesquisaCliente = $('#containerTabelaPesquisaCliente');
		this.htmlTabelaPesquisaRapidaCliente = $('#tabela-pesquisa-rapida-cliente').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisaRapidaCliente);
	}
	
	PesquisaRapidaCliente.prototype.iniciar = function(){		
		this.pesquisarBtn.on('click', onPesquisarClicado.bind(this));
	}
	
	function onPesquisarClicado(event){
		event.preventDefault();
		$.ajax({
			url: this.pesquisaRapidaClienteModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			},
			success: onPesquisaRapidaClienteConcluida.bind(this),
			error: onErroPesquisaRapidaCliente.bind(this)
		});
	}
	
	function onPesquisaRapidaClienteConcluida(data){
		var html = this.template(data);
		this.containerTabelaPesquisaCliente.html(html);
		this.mensagemErro.addClass('hidden');		
	}
	
	function onErroPesquisaRapidaCliente(){
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaCliente;
	
}());

$(function(){
	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});