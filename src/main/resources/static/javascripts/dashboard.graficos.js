var Brewer = Brewer || {};

Brewer.GraficosVendaProMes = (function(){
	
	function GraficosVendaProMes(){
		this.ctx = $('#graficoVendaPorMes')[0].getContext('2d');
	}
	
	GraficosVendaProMes.prototype.iniciar = function(){
		$.ajax({
			url: 'venda/totalPorMes',
			method: 'GET',
			success: onRenderizarGraficoPorMes.bind(this)
		});
	}
	
	function onRenderizarGraficoPorMes(dados){
		var meses = [];
		var valoresProMes = [];
		
		dados.forEach(function(obj){
			meses.unshift(obj.mes);
			valoresPorMes.unshift(obj.total);
		});
		
		var graficoVendaPorMes = new Chart(this.ctx, {
		    type: 'line',
		    data: {
		    	labels: meses,
		    	datasets: [{
		    		label: 'Venda por mês',
		    		backgroundColor: "rgba(26,179,148,0.5)",
		    		pointBorderColor: "rgba(26,179,148,1)",
		    		pointBackgroundColor: "#fff",
		    		data: valoresPorMes
		    	}]
		    }
		});
	}
	
	return GraficosVendaProMes;
	
}());

$(function(){
	var graficoVendaPorMes = new Brewer.GraficosVendaProMes();
	graficoVendaPorMes.iniciar();
})