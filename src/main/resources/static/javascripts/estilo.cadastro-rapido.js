var Brewer = Brewer || {}

Brewer.EstiloCadastroRapido = (function(){
	
	function EstiloCadastroRapido(){		
		this.modalEstilo = $('#modalCadastroRapidoEstilo');
		this.botaoSalvarEstilo = this.modalEstilo.find('.js-cadastro-estilo-rapido-btn');
		this.form = this.modalEstilo.find('form');
		this.url = this.form.attr('action');
		this.inputEstiloNome = $('#input-estilo-nome');
		this.containerMenssageErro = $('.js-mensagem-erro');
		this.inptusEstilo = this.form.find('.form-group');
	}
	
	EstiloCadastroRapido.prototype.iniciar = function(){
		this.form.on('submit', function(event){ event.preventDefault(); } );	
		this.modalEstilo.on('shown.bs.modal', onModalShow.bind(this));
		this.modalEstilo.on('hide.bs.modal', onModalClose.bind(this));		
		this.botaoSalvarEstilo.on('click', botaoSalvarEstiloClick.bind(this));
	}
	
	function onModalShow(){
		this.inputEstiloNome.focus();		
	}
	
	function onModalClose(){
		this.inputEstiloNome.val('');
		this.containerMenssageErro.addClass('hidden');
		this.inptusEstilo.removeClass('has-error');
	}
	
	function botaoSalvarEstiloClick(){
		var nomeEstilo = this.inputEstiloNome.val();
		
		$.ajax({
			url : this.url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({nome: nomeEstilo}),
			error : onErroSalvandoEstilo.bind(this),
			success : onEstiloSalvo.bind(this)
		});		
	}
	
	function onErroSalvandoEstilo(obj){
		var menssageErro = obj.responseText;
		this.containerMenssageErro.removeClass('hidden');
		this.containerMenssageErro.html('<span>'+menssageErro+'</span');
		this.inptusEstilo.addClass('has-error');
	}
	
	function onEstiloSalvo(estilo){
		var comboEstilo = $('#input-cerveja-estilo');
		var options = $('<option>')
		options.val(estilo.codigo);
		options.text(estilo.nome);
		comboEstilo.append(options);
		comboEstilo.val(estilo.codigo);
		this.modalEstilo.modal('hide');
	}
	
	return EstiloCadastroRapido;
	
}());

$(function(){
	var estiloCadastroRapido =  new Brewer.EstiloCadastroRapido();
	estiloCadastroRapido.iniciar();
});