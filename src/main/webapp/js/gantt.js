
//var tasks2 = [{
//    name: 'Sleep',
//    intervals: [{ // From-To pairs
//        from: Date.UTC(0, 0, 0, 0),
//        to: Date.UTC(0, 0, 0, 6)
//    }, {
//        from: Date.UTC(0, 0, 0, 22),
//        to: Date.UTC(0, 0, 0, 24)
//    }]
//}, {
//    name: 'Family time',
//    intervals: [{ // From-To pairs
//        from: Date.UTC(0, 0, 0, 6),
//        to: Date.UTC(0, 0, 0, 8)
//    }, {
//        from: Date.UTC(0, 0, 0, 16),
//        to: Date.UTC(0, 0, 0, 22)
//    }]
//}, {
//    name: 'Eat',
//    intervals: [{ // From-To pairs
//        from: Date.UTC(0, 0, 0, 7),
//        to: Date.UTC(0, 0, 0, 8),
//        label: 'Breakfast'
//    }, {
//        from: Date.UTC(0, 0, 0, 12),
//        to: Date.UTC(0, 0, 0, 12, 30)
//    }, {
//        from: Date.UTC(0, 0, 0, 16),
//        to: Date.UTC(0, 0, 0, 17),
//        label: 'Dinner'
//    }, {
//        from: Date.UTC(0, 0, 0, 20, 30),
//        to: Date.UTC(0, 0, 0, 21)
//    }]
//}, {
//    name: 'Work',
//    intervals: [{ // From-To pairs
//        from: Date.UTC(0, 0, 0, 8),
//        to: Date.UTC(0, 0, 0, 16)
//    }]
//}];
//
//// Define milestones
// var milestones = [{
//    name: 'Get to bed',
//    time: Date.UTC(0, 0, 0, 22),
//    task: 1,
//    marker: {
//        symbol: 'triangle',
//        lineWidth: 1,
//        lineColor: 'black',
//        radius: 8
//    }
// }];

var myChart;

function clearGraph() {
	console.log(myChart.legend);
	while (myChart.series.length > 0) {
		myChart.series[0].remove(true);
	}
}

function createGraph(inclusion, initiales, tts, milestones, toxs, hospits, categs, maxDate, 
		repPoints, prelMilestones) {
	
	Highcharts.setOptions({
	    global : {
	        useUTC : false
	    }
	});
	
	// re-structure the tasks into line seriesvar series = [];
	var series = [];
	$.each(tts.reverse(), function(i,trait) {
	    var item = {
	        name: 'Traitements',
	        color: trait.color,
	        data: []
	    };
	    $.each(trait.intervals, function(j, interval) {
	        item.data.push({
	            x: interval.from,
	            y:  interval.categorie,
	            label: interval.label,
	            start: interval.from,
	            end: interval.to
	        }, {
	            x: interval.to,
	            y:  interval.categorie,
	            label: interval.label,
	            start: interval.from,
	            end: interval.to
	        });
	        
	        // add a null value between intervals
	        if (trait.intervals[j + 1]) {
	            item.data.push(
	                [(interval.end + trait.intervals[j + 1].start) / 2, null]
	            );
	        }

	    });

	    series.push(item);
	});
	
	$.each(toxs.reverse(), function(i, tox) {
	    var item = {
	        name: 'Toxicites',
	        color: tox.color,
	        data: []
	    };
	    $.each(tox.intervals, function(j, interval) {
	        item.data.push({
	            x: interval.from,
	            y: interval.categorie,
	            label: interval.label,
	            start: interval.from,
	            end: interval.to,
	            hasDesc: true, 
	            desc: interval.desc
	        }, {
	            x: interval.to,
	            y: interval.categorie,
	            label: interval.label,
	            start: interval.from,
	            end: interval.to,
	            hasDesc: true,
	            desc: interval.desc
	        });
	        
	        // add a null value between intervals
	        if (tox.intervals[j + 1]) {
	            item.data.push(
	                [(interval.end + tox.intervals[j + 1].start) / 2, null]
	            );
	        }

	    });

	    series.push(item);
	});
	
	$.each(hospits.reverse(), function(i, hosp) {
	    var item = {
	        name: 'Hospitalisation',
	        color: hosp.color,
	        data: []
	    };
	    $.each(hosp.intervals, function(j, interval) {
	        item.data.push({
	            x: interval.from,
	            y: interval.categorie,
	            label: interval.label,
	            hasDesc: true,
	            start: interval.from,
	            end: interval.to,
	            desc: interval.desc
	        }, {
	            x: interval.to,
	            y: interval.categorie,
	            label: interval.label,
	            hasDesc: true,
	            start: interval.from,
	            end: interval.to,
	            desc: interval.desc
	        });
	        
	        // add a null value between intervals
	        if (hosp.intervals[j + 1]) {
	            item.data.push(
	                [(interval.end + hosp.intervals[j + 1].start) / 2, null]
	            );
	        }

	    });
	    series.push(item);
	});
//
//
//	// restructure the visit milestones
	$.each(milestones, function(i, milestone) {

	    var item = Highcharts.extend(milestone, {
	        data: [{
	            x: milestone.time,
	            y: milestone.task,
		        label: milestone.label,
		        name: milestone.name
	        }],
	        type: 'scatter'
	    });
	    series.push(item);
	});
	
	// restructure the progression points
	$.each(repPoints, function(i, reponse) {
	    var item = Highcharts.extend(reponse, {
	        data: [{
	            x: reponse.time,
	            y: reponse.task,
		        label: reponse.label,
		        name: reponse.name,
		        desc: reponse.desc
	        }],
	        type: 'scatter'
	    });
	    series.push(item);
	});
	
	// restructure the prelevements milestones
	$.each(prelMilestones, function(i, pMile) {
	    var item = Highcharts.extend(pMile, {
	        data: [{
	            x: pMile.time,
	            y: pMile.task,
		        label: pMile.label,
		        name: pMile.name,
		        desc: pMile.desc
	        }],
	        type: 'scatter'
	    });
	    series.push(item);
	});
	
	
	myChart = $('$container').highcharts({
	    
		title: {
	        text: 'Suivi Visites-Toxicites: ' + inclusion + " " + initiales
	    },

	    xAxis: {
	        type: 'datetime',
	        max: maxDate
	    },

	    yAxis: {
	    	min: -1,
	    	max: categs.length,
	        tickInterval: 1,
	        labels: {
	            formatter: function() {
	            	return categs[this.value];
	            }
//	                if (labs[this.value]) {
//	                    return labs[this.value].name;
//	                }
//	            }
        },
	        startOnTick: false,
	        endOnTick: false,
	    title: {
	        text: ''
	     },
	            minPadding: 0.2,
	                maxPadding: 0.2
	    },

	    legend: {
	        enabled: false
	    },

	    tooltip: {
	    	useHTML: true,
	        formatter: function() {
	        	if (categs[this.y] == 'Suivi') {
	        		return '<div style="text-decoration: underline; color: blue; cursor: pointer" ' 
	        		+ 'onclick="window.open(\'https://melbase.aphp.fr/OpenClinica/EnterDataForStudyEvent?eventId=' 
	        		+ this.point.label + '\',\'_blank\');return false;">' 
	        		+ this.point.name + '</div><br/>' 
        			+ Highcharts.dateFormat('%e %b %Y', new Date(this.point.x)) ;
	        	} else if (categs[this.y] == 'Réponses' || categs[this.y] == 'Biologie') {
	        		var ttip = '<b>'+ this.point.name + '</b>';
	        		if (this.point.options.desc != '') {
	        			ttip = ttip + '<br/>'
	    	        		+ this.point.options.desc + '<br/>';
	        		} 
	        		return ttip;
	        	} else {
	        		var ttip;
	        		if (this.point.options.hasDesc) {
	        			ttip = '<div style="text-decoration: underline; color: blue; cursor: pointer" ' 
	    	        		+ 'onclick="window.open(\'https://melbase.aphp.fr/OpenClinica/EnterDataForStudyEvent?eventId=' 
	    	        		+ this.point.options.label + '\',\'_blank\');return false;">'
	    	        		+ categs[this.y] + '</div><br/>' 
	    	        		+ this.point.options.desc + '<br/>'
	        		} else {
	        			ttip = '<b>'+ categs[this.y] + '</b><br/>'
	        		}
	        		return ttip	+ Highcharts.dateFormat('%e %b %Y', this.point.options.start)  +
	        			' - ' + Highcharts.dateFormat('%e %b %Y', this.point.options.end); 
	        	}
	        }
	    },

	    plotOptions: {
	        line: {
	            lineWidth: 9,
	            marker: {
	                enabled: false
	            },
//	            dataLabels: {
//	                enabled: true,
//	                align: 'left',
//	                formatter: function() {
//	                    return this.point.options && this.point.options.label;
//	                }
//	            }
	        }
	    },

	    series: series

	});
}

// $(createGraph(tasks2, milestones2));