Brewer = Brewer || {};

Brewer.ExlcusaoDialogo = (function(){
	
	function ExlcusaoDialogo(){
		this.btnExcluir = $('.js-excluir-btn')
	}
	
	ExlcusaoDialogo.prototype.iniciar = function(){
		this.btnExcluir.on('click', onBtnExcluirClick.bind(this));
		
		if (window.location.search.indexOf('excluido') > -1){
			swal('Pronto!', 'Excluído com sucesso', 'success');
		}
	}
	
	function onBtnExcluirClick(evento){
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var objeto = botaoClicado.data('objeto');
		var nomeObjeto = botaoClicado.data('nome-objeto');
		
		swal({
			title: 'Tem certeza ?',
			text: 'Deseja realmente exlcuir '+ nomeObjeto +' "'+ objeto +'" ?',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, excluir agora!',
			closeOnConfirm: false
		}, onExcluirConfirmClick.bind(this, url));		
	}
	
	function onExcluirConfirmClick(url){
		$.ajax({
			url: url,
			method: 'DELETE',
			success: onExcluirSuccess.bind(this),
			error: onExcluirError.bind(this)
		});
	}
	
	function onExcluirSuccess(){
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl  = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;
	}
	
	function onExcluirError(e){
		swal('Ops!', e.responseText, 'error');
	}
	
	return ExlcusaoDialogo;	
}());

$(function(){
	var dialogoExclusao = new Brewer.ExlcusaoDialogo();
	dialogoExclusao.iniciar();
});