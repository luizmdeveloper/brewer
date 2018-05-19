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
		this.pesquisaRapidaClienteModal.on('shown.bs.modal', onModalShow.bind(this));		
	}
	
	function onModalShow(){
		this.nomeInput.focus();
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
		this.mensagemErro.addClass('hidden');		

		var html = this.template(data);
		this.containerTabelaPesquisaCliente.html(html);
		
		var tabelaPesquisaClienteRapida = new Brewer.TabelaPesquisaClienteRapida(this.pesquisaRapidaClienteModal);
		tabelaPesquisaClienteRapida.iniciar();
	}
	
	function onErroPesquisaRapidaCliente(){
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaCliente;	
}());

Brewer.TabelaPesquisaClienteRapida = (function(){
	
	function TabelaPesquisaClienteRapida(modalPesquisa){
		this.modal = modalPesquisa;
		this.cliente = $('.js-cliente-pesquisa-rapida');
	}
	
	TabelaPesquisaClienteRapida.prototype.iniciar = function(){
		this.cliente.on('click', onClienteSelecionado.bind(this));
	}
	
	function onClienteSelecionado(event){
		var clienteSelecionado = $(event.currentTarget);
		this.modal.modal('hide');
		
		$('#nomeCliente').val(clienteSelecionado.data('nome'));
		$('#codigoCliente').val(clienteSelecionado.data('codigo'));
	}
	
	return TabelaPesquisaClienteRapida;
	
}())

$(function(){
	var pesquisaRapidaCliente = new Brewer.PesquisaRapidaCliente();
	pesquisaRapidaCliente.iniciar();
});
