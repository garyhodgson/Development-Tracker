jQuery(document).ready(function () {
	
	var developments
	
	function beginAddNodesLoop(graph) {
    	
    	if (Modernizr.localstorage) {
   			localDevs = localStorage.getItem("developments")
   			
   	    	if (localDevs != undefined){
   	    		developments = jQuery(jQuery.parseXML(localDevs))
   	    		
    			var hash = developments.find('developments').attr('hash')
   	    		
				if (hash && allDevelopmentsHash == hash){
					console.log("displaying")
					display()
					return
				} else {
					console.log("resetting")
					// reset developments for full update
					developments = null
					localStorage.removeItem("developments")
				}		
   	    	} 
    	}
    	
    	loadAndDisplay()
    }
	
	function loadAndDisplay(){
		jQuery("#vis-load").dialog('open')
		
		jQuery.get('/api/v1/graph', function (data) {
			developments = jQuery(data)
			if (Modernizr.localstorage) {
	        	var xmlstr = data.xml ? data-xml : (new XMLSerializer()).serializeToString(data);
	        	localStorage.setItem("developments",xmlstr)
    		}
			
			display()
        }, "xml").complete(function(){
        	jQuery("#vis-load").dialog('close')
        });
	}
	
	function display(){
		if (targetDevelopmentId == null){
	    	showAll(graph)
		} else {
			showDev(graph)	
		}
	}
	
	function showDev(graph){
		
		var dev = developments.find('development[id='+targetDevelopmentId+']')
		
		var id = dev.attr('id');
		var title = dev.find('title').text();
		var image = dev.find('image')
		var thumbnail = image.attr('thumbnail')
		var createdby = dev.find('createdBy').text();
		var uri = dev.attr('uri');
		
		graph.addNode(id, {
		    thumbnail: thumbnail,
		    title: title,
		    uri: uri,
		    isRoot: true
		});
		var connectionsLimit = jQuery('#connectionsRange').val()
		var connectionsShown = new Array()
		showConnections(graph,dev, connectionsLimit, connectionsShown)		
		showReverseConnections(graph,dev, connectionsLimit, connectionsShown)
		showDetail(dev)
	}
	
	function showAll(graph){
		var devs = developments.find('development')
		var count = devs.length
		if (!confirm("Loading all developments ("+count+") may result in poor performance. Continue?")){
			return
		}
		
		devs.each(function (i, v) {
            var dev = jQuery(this);

            var title = dev.find('title').text();
            var image = dev.find('image')
            var id = dev.attr('id');
            var createdby = dev.find('createdBy').text();
            var uri = dev.attr('uri');

            graph.addNode(id, {
                thumbnail: image.attr('thumbnail'),
                title: title,
                uri: uri
            });
        });
		
		devs.each(function (i, v) {
            var dev = jQuery(this);
            var id = dev.attr('id');
            var connections = dev.find('connection')
            connections.each(function (j, c) {
                var conn = jQuery(this);
                var toNodeId = conn.attr('to')
                if (toNodeId != undefined) {
                    var toNode = graph.getNode(toNodeId)
                    if (toNode == undefined) {
                        graph.addNode(toNodeId, {
                            title: conn.text(),
                            uri: conn.attr('url')
                        });
                    }
                    graph.addLink(id, conn.attr('to'), {
                        type: conn.attr('type')
                    });
                }
            });

        });
		
	}
	
	function showConnections(graph, dev, level, connectionsShown){

		if (level == 0) {
			return 
		}
		
		var id = dev.attr('id');
		dev.find('connection').each(function (j, c) {
		    var conn = jQuery(this);
		    var toNodeId = conn.attr('to')
		    if (toNodeId != undefined && toNodeId != id) {
		        var toNode = graph.getNode(toNodeId)
		        if (toNode == undefined) {
		        	var toDev = developments.find('development[id='+toNodeId+']')
		            graph.addNode(toNodeId, {
		            	thumbnail: toDev.find('image').attr('thumbnail'),
		                title: conn.text(),
		                uri: conn.attr('url')
		            });
		        	showConnections(graph, toDev, level-1, connectionsShown)
		        	showReverseConnections(graph, toDev, level-1, connectionsShown)
		        }
		        //if (connectionsShown.indexOf(conn.attr('id')) == -1){
			        graph.addLink(id, conn.attr('to'), {
			            type: conn.attr('type')
			        });
			    //    connectionsShown.push(conn.attr('id'))		        	
		        //}
		    }
		   });
	}
	
	function showReverseConnections(graph, dev, level, connectionsShown){

		if (level == 0) {
			return 
		}
		var id = dev.attr('id');
		dev.find('reverseConnection').each(function (j, c) {
		    var conn = jQuery(this);
		    var fromNodeId = conn.attr('from')
		    if (fromNodeId != undefined && fromNodeId != id) {
		        var fromNode = graph.getNode(fromNodeId)
		        if (fromNode == undefined) {
		        	var fromDev = developments.find('development[id='+fromNodeId+']')
		            graph.addNode(fromNodeId, {
		            	thumbnail: fromDev.find('image').attr('thumbnail'),
		                title: conn.text(),
		                uri: conn.attr('url')
		            });
		        	showConnections(graph, fromDev, level-1, connectionsShown)
		        	showReverseConnections(graph, fromDev, level-1, connectionsShown)
		        }
		        //if (connectionsShown.indexOf(conn.attr('id')) == -1){
			        graph.addLink(id, conn.attr('from'), {
			            type: conn.attr('type')
			        });
			    //    connectionsShown.push(conn.attr('id'))		       
		        //}
		    }
		   });		
	}
	
	function showDetail(dev){
		jQuery('#details').html('<a href="'+dev.attr('uri')+'"><h5>'+dev.find('title').text()+'</h5></a><img src="'+dev.find('image').attr('thumbnail')+'"/><p>'+dev.find('description').text()+'</p>')
	}
	
	function showMissingDetail(title, id){
		jQuery('#details').html('<a href="/development/'+id+'"><h5>'+title+'</h5></a>')
	}
		
	jQuery("#vis-load").dialog({ 
		autoOpen: false,
		dialogClass:'',
		position: 'center',
		modal: true,
		height: 'auto',
		resizable: false,
		title: ' ',
		closeText: ''
	});
	
	

    function imageClickHandler(evt) {    	
        if (2 == evt.detail) {
            location = "/development/"+evt.target.getAttribute('id')+"/graph"
        } else {
        	var dev = developments.find('development[id='+evt.target.id+']')
        	if (dev.length > 0){
        		showDetail(dev)
        	} else {
        		var title = '[Unknown]'
        		if (evt.target.getElementsByTagName("title").length > 0){
        			title = evt.target.getElementsByTagName("title")[0].text()
        		}        		
        		showMissingDetail(title, evt.target.id)
        	}        	
        }
    }
    
    jQuery('#connectionsRange').change(function(){
    	jQuery("#connectionsRangeDisplay").html(jQuery(this).val())
    	graph.clear()
    	beginAddNodesLoop(graph)
    })

    graph = Viva.Graph.graph();

    var layout = Viva.Graph.Layout.forceDirected(graph, {
        springLength: 150,
        springCoeff: 0.0005,
        dragCoeff: 0.007,
        gravity: -3
    });

    var graphics = Viva.Graph.View.svgGraphics();

    graphics.link(function (link) {
        var x = Viva.Graph.svg('line')
        .attr('stroke-width', '2').attr('marker-end', 'url(#Triangle)')

        if (link.data) {
            switch (link.data.type) {
            case "Derived From":
            case "Derived":
                x.attr('stroke', 'lightblue')
                break;
            case "Related To":
                x.attr('stroke', 'red')
                break;
            case "Designed For":
            case "Has Design":
                x.attr('stroke', 'green')
                break;
            case "Implementation Of":
            case "Implementation By":
                x.attr('stroke', 'yellow')
                break;
            case "Part Of":
            case "Consists Of":
            	x.attr('stroke', 'orange')
                break;
            case "Consists Of":
            case "Part Of":
                x.attr('stroke', 'orange')
                break;
            case "Inspired By":
            case "Inspired":
                x.attr('stroke', 'maroon')
                break;
            case "Evolved From":
            case "Evolved Into":
                x.attr('stroke', 'olive')
                break;
            case "In Answer To":
            case "Is Answered By":
                x.attr('stroke', 'purple')
                break;
            case "CreatedBy":
            	x.attr('stroke', 'teal')
                x.attr('stroke-width', '1')                
                break;
            default:
                x.attr('stroke', 'grey')
            }

            x.append(Viva.Graph.svg('title').text(link.data.type));

        }
        return x
    });


    graphics.node(function (node) {
        var x;

        if (node.data && node.data.thumbnail) {
        	
            x = Viva.Graph.svg('image').attr('width', 44).attr('height', 44).attr('id', node.id).link(node.data.thumbnail)
                        
            if (node.data.isRoot){
            	x.attr('width', 88).attr('height', 88).attr('style','border:2px white solid')
            }
            
        } else {
            x = Viva.Graph.svg('rect').attr('width', 40).attr('height', 40).attr('fill', '#888').attr('id', node.id)
        }

        if (node.data && node.data.title) {
            x.append(Viva.Graph.svg('title').text(node.data.title))
        }

        x.addEventListener('click', imageClickHandler, false)

        return x;

    }).placeNode(function (nodeUI, pos) {
        // Shift image to let links go to the center:
        nodeUI.attr('x', pos.x - 22).attr('y', pos.y - 22);
    })

    renderer = Viva.Graph.View.renderer(graph, {
        layout: layout,
        graphics: graphics,
        container: document.getElementById('graph'),
        renderLinks: true
    });

    renderer.run(50);

    var defs = Viva.Graph.svg('defs');
    var marker = Viva.Graph.svg('marker');
    marker.attr('id', 'Triangle');
    marker.attr('viewBox', '0 0 10 10');
    marker.attr('refX', '32');
    marker.attr('refY', '5');
    marker.attr('markerUnits', 'strokeWidth');
    marker.attr('markerWidth', '6');
    marker.attr('markerHeight', '5');
    marker.attr('orient', 'auto');
    marker.attr('stroke', 'black');
    marker.attr('fill', 'white');
    var path = Viva.Graph.svg('path');
    marker.append(path);
    path.setAttribute('d', 'M 0 0 L 10 5 L 0 10 z');
    jQuery(graphics.getSvgRoot()).prepend(defs);
    defs.append(marker);
    
    beginAddNodesLoop(graph);
    
    g = graph
    l = layout

})