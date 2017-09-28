$(function(){
	
	var modalEstilo = $('#modalCadastroRapidoEstilo');
	var botaoSalvarEstilo = modalEstilo.find('.js-cadastro-estilo-rapido-btn');
	var form = modalEstilo.find('form');
	var url = form.attr('action');
	var inputEstiloNome = $('#input-estilo-nome');
	var containerMenssageErro = $('.js-mensagem-erro');
	var inptusEstilo = form.find('.form-group');
	
	
	form.on('submit', function(event){ event.preventDefault(); } );
	
	modalEstilo.on('shown.bs.modal', onModalShow);
	modalEstilo.on('hide.bs.modal', onModalClose);
	
	function onModalShow(){
		inputEstiloNome.focus();
		containerMenssageErro.addClass('hidden');
		inptusEstilo.removeClass('has-error');
	}
	
	function onModalClose(){
		inputEstiloNome.val('');
	}
	
	botaoSalvarEstilo.on('click', botaoSalvarEstiloClick);
	
	function botaoSalvarEstiloClick(){
		var nomeEstilo = inputEstiloNome.val();
		
		$.ajax({
			url : url,
			method : 'POST',
			contentType : 'application/json',
			data : JSON.stringify({nome: nomeEstilo}),
			error : onErroSalvandoEstilo,
			success : onEstiloSalvo
		});		
	}
	
	function onErroSalvandoEstilo(obj){
		var menssageErro = obj.responseText;
		containerMenssageErro.removeClass('hidden');
		containerMenssageErro.html('<span>'+menssageErro+'</span');
		inptusEstilo.addClass('has-error');
	}
	
	function onEstiloSalvo(estilo){
		var comboEstilo = $('#input-cerveja-estilo');
		var options = $('<option>')
		options.val(estilo.codigo);
		options.text(estilo.nome);
		comboEstilo.append(options);
		comboEstilo.val(estilo.codigo);
		modalEstilo.modal('hide');
	}

});