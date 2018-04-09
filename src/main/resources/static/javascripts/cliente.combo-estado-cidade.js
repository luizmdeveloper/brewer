Brewer = Brewer || {};

Brewer.ComboEstado = (function(){
	
	function ComboEstado(){
		this.combo = $('#input-cliente-estado');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboEstado.prototype.iniciar = function(){
		this.combo.on('change', onComboEstado.bind(this));
	}
	
	function onComboEstado(){
		this.emitter.trigger('estado', this.combo.val());
	}
	
	return ComboEstado;
	
}());

Brewer.ComboCidade = (function(){
	
	function ComboCidade(comboEstado){
		this.comboEstado = comboEstado;
		this.combo = $('#input-cliente-cidade');
		this.imageLoading = $('.js-image-loading');
		this.inputHiddenCidadeSelecionada = $('#inputHiddenCidadeSelecionada');
	}
	
	ComboCidade.prototype.iniciar = function(){
		reset.call(this);
		this.comboEstado.on('estado', onEstadoAlterado.bind(this));
		var codigoEstado = this.combo.val();
		inicializarCidades.call(this, codigoEstado);
	}
	
	function inicializarCidade(codigoEstado){
		if (codigoEstado){
			var resposta = $.ajax({
				url: this.combo.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'estado': codigoEstado }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			
			resposta.done(onBuscarCidadesFinalizado.bind(this));
		} else {
			reset.call(this);			
		}				
	}
	
	function onEstadoAlterado(evento, codigoEstado){
		this.inputHiddenCidadeSelecionada.val('');
		inicializarCidades.call(this, codigoEstado);	
	}
	
	function onBuscarCidadesFinalizado(cidades) {
		var options = [];
		cidades.forEach(function(cidade) {
			options.push('<option value"' + cidade.codigo + '">' + cidade.nome + '</option>');
		});
		this.combo.html(options.join(''));
		this.combo.removeAttr('disabled');
		var codigoCidadeSelecionada = this.inputHiddenCidadeSelecionada.val();
		if (codigoCidadeSelecionada) {
			this.combo.val(codigoCidadeSelecionada);
		}

	}
	
	function reset(){
		this.combo.html('<option value="">Selecione uma cidade</option>');
		this.combo.val('');
		this.combo.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao(){
		reset.call(this);
		this.imageLoading.show();
	}
	
	function finalizarRequisicao(){
		this.imageLoading.hide();
	}
	
	return ComboCidade;
	
}());

$(function(){
	
	var comboEstado = new Brewer.ComboEstado();
	comboEstado.iniciar();
	
	var comboCidade = new Brewer.ComboCidade(comboEstado);
	comboCidade.iniciar();
	
});