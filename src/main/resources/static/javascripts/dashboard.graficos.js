var Brewer = Brewer || {};

Brewer.GraficoVendaProMes = (function(){
	
	function GraficoVendaProMes(){
		this.ctx = $('#graficoVendaPorMes')[0].getContext('2d');
	}
	
	GraficoVendaProMes.prototype.iniciar = function(){
		$.ajax({
			url: 'venda/totalPorMes',
			method: 'GET',
			success: onRenderizarGraficoPorMes.bind(this)
		});
		
	}
	
	function onRenderizarGraficoPorMes(dados){
		var meses = [];
		var valoresPorMes = [];
		
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
		
	return GraficoVendaProMes;
	
}());

Brewer.GraficoVendaPorOrigem = (function(){
	
	function GraficoVendaPorOrigem(){
		this.ctx = $('#barChart')[0].getContext('2d');		
	}
	
	GraficoVendaPorOrigem.prototype.iniciar = function(){
		$.ajax({
			url: 'venda/totalPorOrigem',
			method: 'GET',
			success: onRederizarGraficoPorOrigem.bind(this)
		});
	}

	function onRederizarGraficoPorOrigem(dados){
		var meses = [];
		var totalNacional = [];
		var totalInternacional = [];
		
		dados.forEach(function(obj){
			meses.unshift(obj.mes);
			totalNacional.unshift(obj.totalNacional);
			totalInternacional.unshift(obj.totalInternacional);
		});
		
		var graficoVendaPorMes = new Chart(this.ctx, {
		    type: 'bar',
		    data: {
		    	labels: meses,
		    	datasets: [{
		    		label: 'Nacional',
		    		backgroundColor: "rgba(220,220,220,0.5)",
		    		data: totalNacional
		    	},
		    	{
		    		label: 'Internacional',
		    		backgroundColor: "rgba(26,179,148,0.5)",
		    		data: totalInternacional
		    	}
		    	]
		    }
		});
	}

	return GraficoVendaPorOrigem;
}());

$(function(){
	var graficoVendaPorMes = new Brewer.GraficoVendaProMes();
	graficoVendaPorMes.iniciar();
	
	var graficoPorOrigem = new Brewer.GraficoVendaPorOrigem();
	graficoPorOrigem.iniciar();	
})
