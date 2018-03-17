var Brewer = Brewer || {};

Brewer.UploadFoto = (function(){
	
	function UploadFoto(){
		this.inputFotoCerveja = $('input[name=foto]');
		this.inputContentType = $('input[name=contentType]');
		this.uploadFoto = $('#upload-drop');
		
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
				complete: onCompleteUpload.bind(this)
		}
		
		UIkit.uploadSelect($('#upload-select'), settings);
		UIkit.uploadDrop(this.uploadFoto, settings);		
	}
		
	function onCompleteUpload(resposta){
		this.inputFotoCerveja.val(resposta.nomeFoto);
		this.inputContentType.val(resposta.contentType);					
		
		console.log(resposta);
		
		this.uploadFoto.addClass('hidden');
		var htmlFotoCerveja = this.template({nomeFoto: resposta.nomeFoto});
		this.containerFotoCerveja.append(htmlFotoCerveja);
		
		$('.js-remove-foto').on('click', onRemoveFoto.bind(this));
	}
	
	function onRemoveFoto(){
		this.inputFotoCerveja.val('');
		this.inputContentType.val('');
		this.uploadFoto.removeClass('hidden');	
		$('.js-foto-cerveja').remove();
	}
		
	return UploadFoto;
})();

$(function(){
	var uploadFoto =  new Brewer.UploadFoto();
	uploadFoto.iniciar();
});