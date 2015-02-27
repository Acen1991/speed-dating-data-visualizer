angular.module("speed-data-app", []).controller("baseController", ['$scope', function($scope) {}]);

/* implementation heavily influenced by http://bl.ocks.org/1166403 */
/* some arguments AGAINST the use of dual-scaled axes line graphs can be found at http://www.perceptualedge.com/articles/visual_business_intelligence/dual-scaled_axes.pdf */

// define dimensions of graph

d3.json("js/data.json", function(error,json){
	 if (error) return console.warn(error);
  	 vizualize(json);
})

var vizualize = function(speed_dating_data){
	var m = [80, 80, 80, 80]; // margins
	var w = 1200 - m[1] - m[3]; // width
	var h = 500 - m[0] - m[2]; // height

	var ym_range = d3.scale.ordinal().domain(speed_dating_data.attributes_data.m_axis_values).rangePoints([0, 400]);
	//for example to know where is the position of "engineer" on the axis we need to call ym_range.call('scale','engineer')
	yf_range = d3.scale.ordinal().domain(speed_dating_data.attributes_data.f_axis_values).rangePoints([0, 400]);
	//for example to know where is the position of    "2K"    on the axis we need to call yf_range.call('scale','2K')

	// Add an SVG element with the desired dimensions and margin.
	var graph = d3.select("#graph")
	    .append("g")
	    .attr("transform", "translate(" + m[3] + "," + m[0] + ")");

	// create left yAxis
	var ymAxis = d3.svg.axis().scale(ym_range).ticks(4).orient("left");
	// Add the y-axis to the left
	graph.append("g")
	    .attr("class", "y axis axisLeft")
	    .attr("transform", "translate(-15,0)")
	    .call(ymAxis);

	// create right yAxis
	var yfAxis = d3.svg.axis().scale(yf_range).ticks(6).orient("right");
	// Add the y-axis to the right
	graph.append("g")
	    .attr("class", "y axis axisRight")
	    .attr("transform", "translate(" + (w + 15) + ",0)")
	    .call(yfAxis);

	graph.selectAll("g")
		.data(speed_dating_data.values, function(d,i){
			if(typeof d == "object"){
				return d.couple_id;
			} else {
				return d;
			}
		})
		.enter()
			.append("g")
			.append("polygon")
				.attr("points",function(d,i){
					var ymScaledPosition = ym_range.call('scale',d.m_attribute_value);
					var yfScaledPosition = yf_range.call('scale',d.f_attribute_value);
					var m_success_factor = d.m_success_attribute*25;
					var f_success_factor = d.f_success_attribute*25;


					var topLeftPoint = [-15, ymScaledPosition-m_success_factor];
					var topRightPoint = [w+15 , yfScaledPosition-f_success_factor];
					var bottomRightPoint = [w+15, yfScaledPosition+f_success_factor];
					var bottomLeftPoint = [-15, ymScaledPosition+m_success_factor];
					
					var allPoints = [];
					allPoints.push(topLeftPoint.join(','));
					allPoints.push(topRightPoint.join(','));
					allPoints.push(bottomRightPoint.join(','));
					allPoints.push(bottomLeftPoint.join(','));
				
					return allPoints.join(' ');
				})
				.attr("style",function(d){
					var fill = "fill:rgb("+[d3.round(255*d.m_like_f),d3.round(255*d.m_like_f),d3.round(255*d.m_like_f)].join(",")+")";
					var strokeWidth="stroke-width:1";
					var stroke = "stroke:purple";

					var styles = [];
					styles.push(fill);
					styles.push(strokeWidth);
					styles.push(stroke);

					return styles.join(";");
				});
}
