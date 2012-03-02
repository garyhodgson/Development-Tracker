<html>
<head>
<title></title>
<script type="text/javascript" src="/js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="/js/vivagraph.min.js"></script>

<script type='text/javascript'>
           
			function beginAddNodesLoop(graph){
				jQuery.get('/api/v1/developments', function(data) {
				  
						jQuery(data).find('development').each(function(i,v){
							var dev = jQuery(this);
							console.log(dev);
							var title = dev.find('title').text();
							var image = dev.find('image')
							var id = dev.attr('id');
							var createdby = dev.find('createdBy').text();
							var uri = dev.attr('uri');
							
							graph.addNode(id, {	url : image.attr('thumbnail'),
												title : title,
												uri : uri});
							//graph.addNode(createdby, {	url : 'http://www.gravatar.com/avatar/55502f40dc8b7c769880b10874abc9d0?s=175&d=mm', title : createdby});
							//graph.addLink(id, createdby);
							
							var connections = dev.find('connection')
							
							connections.each(function(j,c){
								var conn = jQuery(this);
								graph.addLink(id, conn.attr('id'));
							});
							
						});
						
				});
			}

           function onLoad() {
               var graph = Viva.Graph.graph();

               var layout = Viva.Graph.Layout.forceDirected(graph, {
            	   springLength : 100,
            	   springCoeff : 0.0005,
            	    dragCoeff : 0.02,
            	    gravity : -1.2
               });
                
               var graphics = Viva.Graph.View.svgGraphics();
               graphics.node(function(node){
            	  var x;
                  
            	  if (node.data && node.data.url){
					x = Viva.Graph.svg('image')
                      .attr('width', 44)
                      .attr('height', 44)
                      .link(node.data?node.data.url:'');  
                  } else {
                	 x = Viva.Graph.svg('rect')
                     .attr('width', 40)
                     .attr('height', 40)
                     .attr('fill', '#00a2e8');                	 
                  }  
            	  
            	  if (node.data && node.data.title){
            		  x.append(Viva.Graph.svg('title').text(node.data.title));
            	  }
            	  
            	  //if (node.data && node.data.uri){
            		//  x.attr('onclick', 'location= "'+node.data.uri+'"');            		  
            	  //}
            	  
            	  
                  return x;
                  
               }).placeNode(function(nodeUI, pos){
                   // Shift image to let links go to the center:
                   nodeUI.attr('x', pos.x - 22).attr('y', pos.y - 22);
               });
                    
               var renderer = Viva.Graph.View.renderer(graph,
                   {
                       layout     : layout,
                       graphics   : graphics,
                       container  : document.getElementById('graph1'),
                       renderLinks : true
                   });
                   
               renderer.run(50);
                
               beginAddNodesLoop(graph);
               l = layout;
           }
        </script>
<style type='text/css'>
.node {
	background-color: #00a2e8;
	width: 10px;
	height: 10px;
	position: absolute;
}

.link {
	background-color: #dddddd;
	position: absolute;
}

#graph1 {
	position: absolute;
	width: 100%;
	height: 100%;
}
</style>
</head>
<body onload="onLoad()" style="background-color: black;">
	<div id='graph1'></div>
</body>
</html>
