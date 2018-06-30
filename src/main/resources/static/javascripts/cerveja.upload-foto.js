var Brewer = Brewer || {};

Brewer.UploadFoto = (function(){
	
	function UploadFoto(){
		this.inputFotoCerveja = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.uploadFoto = $('#upload-drop');
		this.novaFoto = $('input[name=novaFoto]');
		
		this.containerFotoCerveja = $('.js-container-foto');						
		this.htmlFotoCervejaTemplate  = $('#foto-cerveja').html();
		this.template = Handlebars.compile(this.htmlFotoCervejaTemplate);	
	}
	
	UploadFoto.prototype.iniciar = function() {
		var settings = {
				type: 'json',
				filelimit: 1,
				allow: '*.(jpg|jpeg|png)',
				action : this.containerFotoCerveja.data('url-upload'),
				complete: onCompleteUpload.bind(this),
				beforeSend: addCsrfToken
		}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadFoto, settings);
		
		if (this.inputFotoCerveja.val()){
			renderizarFoto.call(this, {nomeFoto: this.inputFotoCerveja.val(), contentType: this.inputContentType.val()});
		}
	}
		
	function onCompleteUpload(resposta){
		this.novaFoto.val('true');
		rendereizarFoto.call(this, resposta);
	}
	
	function renderizarFoto(resposta){
		this.inputFotoCerveja.val(resposta.nomeFoto);
		this.inputContentType.val(resposta.contentType);
		
		var foto = '';		
		if (this.novaFoto.val() == 'true') {
			foto = 'temp/';
		}
		foto += resposta.nomeFoto;
		
		this.uploadFoto.addClass('hidden');
		var htmlFotoCerveja = this.template({foto: foto});
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));
	}
	
	function onRemoveFoto(){
		this.inputFotoCerveja.val('');
		this.inputContentType.val('');
		this.uploadFoto.removeClass('hidden');	
		$('.js-foto-cerveja').remove();
		this.novaFoto.val('false');
	}
	
	function addCsrfToken(xhr){
		var token = $('input[name=token]').val();
		var header = $('input[name=headerName]').val();
		
		xhr.setRequestHeader(header, token);
	}
		
	return UploadFoto;
})();

$(function(){
	var uploadFoto =  new Brewer.UploadFoto();
	uploadFoto.iniciar();
});