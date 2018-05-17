Brewer = Brewer || {};

Brewer.MultiSelecao = (function(){
	
	function MultiSelecao(){
		this.statusBtn = $('.js-status-btn');
		this.selecaoCheckbox = $('.js-selecao');
		this.selecaoTodosCheckbox = $('.js-selecao-todos');
	}
	
	MultiSelecao.prototype.iniciar = function(){
		this.statusBtn.on('click', onStatusBtnClicado.bind(this));
		this.selecaoTodosCheckbox.on('click', onSelecaoTodosCheckboxClilado.bind(this));
		this.selecaoCheckbox.on('click', onSelecaoCheckboxClicado.bind(this));
	}
	
	function onStatusBtnClicado(event){
		var botaoClicado = $(event.currentTarget);
		var status = botaoClicado.data('status');
		var url    = this.statusBtn.data('url'); 
		
		var checkboxSelecionados = this.selecaoCheckbox.filter(':checked');
		var codigos = $.map(checkboxSelecionados, function(c){
			return $(c).data('codigo');
		});
		
		if (codigos.length > 0){
			$.ajax({
				url: url,
				method: 'PUT',
				data: {
					codigos: codigos,
					status: status
				},
				success: function() {
					window.location.reload();
				}
			});
		}
	}
	
	function onSelecaoTodosCheckboxClilado(){
		var status = this.selecaoTodosCheckbox.prop('checked');
		this.selecaoCheckbox.prop('checked', status);
		habilitarDesabilitarBotoesAcao.call(this, status);
	}
	
	function onSelecaoCheckboxClicado(){
		var status = this.selecaoCheckbox.filter(':checked').length >= this.selecaoCheckbox.length;
		this.selecaoTodosCheckbox.prop('checked', status);
		habilitarDesabilitarBotoesAcao.call(this, this.selecaoCheckbox.filter(':checked').length > 0);		
	}
	
	function habilitarDesabilitarBotoesAcao(ativar){
		ativar ? this.statusBtn.removeClass('disabled') : this.statusBtn.addClass('disabled');
	}
	
	return MultiSelecao;
	
}());

$(function(){	
	var multiSelecao = new Brewer.MultiSelecao();
	multiSelecao.iniciar();	
});