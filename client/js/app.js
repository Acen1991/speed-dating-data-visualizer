angular.module("speed-data-app", []).controller("baseController", ['$scope', function($scope) {}]);

(function(){
d3.json("js/data.json", function(error,json){
	 if (error) return console.warn(error);
  	 vizualize(json);
});

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

	var enter = graph.selectAll("g")
		.data(speed_dating_data.values, function(d,i){
			if(typeof d == "object"){
				return d.couple_id;
			} else {
				return d;
			}
		}).enter();

		enter.append("g")
			.append("polygon")
				.attr("points",function(d,i){

					var ymScaledPosition = ym_range.call('scale',d.m_attribute_value);
					var yfScaledPosition = yf_range.call('scale',d.f_attribute_value);
					var m_success_factor = d.m_success_attribute*25;
					var f_success_factor = d.f_success_attribute*25;

					var topLeftPoint = {x : -15, y : (ymScaledPosition-m_success_factor)};
					var topRightPoint = {x : w+15 , y : (yfScaledPosition-f_success_factor)};
					var bottomRightPoint = {x : w+15, y : (yfScaledPosition+f_success_factor)};
					var bottomLeftPoint = {x : -15, y : (ymScaledPosition+m_success_factor)};

					var topLineEquation = straightEquation(topLeftPoint.x, topLeftPoint.y, topRightPoint.x, topRightPoint.y);
					var bottomLineEquation = straightEquation(bottomLeftPoint.x, bottomLeftPoint.y, bottomRightPoint.x, bottomRightPoint.y);

					var tierTopRightPoint = {x : 0.3*(w+15),  y : topLineEquation(0.3*w+15)};
					var tierBottomRightPoint = {x : 0.3*(w+15), y : bottomLineEquation(0.3*(w+15))};


					var allPoints = [];
					allPoints.push(topLeftPoint.x +','+ topLeftPoint.y);
					allPoints.push(tierTopRightPoint.x + ',' + tierTopRightPoint.y);
					allPoints.push(tierBottomRightPoint.x + ',' + tierBottomRightPoint.y);
					allPoints.push(bottomLeftPoint.x + ',' + bottomLeftPoint.y);
				
					return allPoints.join(' ');
				})
				.attr("style",function(d){
					return commonStyleFunction(d, d.m_like_f);
				});

				enter
				.append("g")
					.append("polygon")
					.attr("points",function(d,i){

						var ymScaledPosition = ym_range.call('scale',d.m_attribute_value);
						var yfScaledPosition = yf_range.call('scale',d.f_attribute_value);
						var m_success_factor = d.m_success_attribute*25;
						var f_success_factor = d.f_success_attribute*25;

						var topLeftPoint = {x : -15, y : (ymScaledPosition-m_success_factor)};
						var topRightPoint = {x : w+15 , y : (yfScaledPosition-f_success_factor)};
						var bottomRightPoint = {x : w+15, y : (yfScaledPosition+f_success_factor)};
						var bottomLeftPoint = {x : -15, y : (ymScaledPosition+m_success_factor)};

						var topLineEquation = straightEquation(topLeftPoint.x, topLeftPoint.y, topRightPoint.x, topRightPoint.y);
						var bottomLineEquation = straightEquation(bottomLeftPoint.x, bottomLeftPoint.y, bottomRightPoint.x, bottomRightPoint.y);

						var tierTopRightPoint = {x : 0.3*(w+15),  y : topLineEquation(0.3*(w+15))};
						var tierBottomRightPoint = {x : 0.3*(w+15), y : bottomLineEquation(0.3*(w+15))};

						var twoTierTopRightPoint = {x : 0.6*(w+15),  y : topLineEquation(0.6*(w+15))};
						var twoTierBottomRightPoint = {x : 0.6*(w+15), y : bottomLineEquation(0.6*(w+15))};


						var allPoints = [];
						allPoints.push(tierTopRightPoint.x +','+ tierTopRightPoint.y);
						allPoints.push(twoTierTopRightPoint.x + ',' + twoTierTopRightPoint.y);
						allPoints.push(twoTierBottomRightPoint.x + ',' + twoTierBottomRightPoint.y);
						allPoints.push(tierBottomRightPoint.x + ',' + tierBottomRightPoint.y);
					
						return allPoints.join(' ');
					})
					.attr("style",function(d){
						return commonStyleFunction(d, d.both_like);
					});

				enter
				.append("g")
					.append("polygon")
					.attr("points",function(d,i){

						var ymScaledPosition = ym_range.call('scale',d.m_attribute_value);
						var yfScaledPosition = yf_range.call('scale',d.f_attribute_value);
						var m_success_factor = d.m_success_attribute*25;
						var f_success_factor = d.f_success_attribute*25;

						var topLeftPoint = {x : -15, y : (ymScaledPosition-m_success_factor)};
						var topRightPoint = {x : w+15 , y : (yfScaledPosition-f_success_factor)};
						var bottomRightPoint = {x : w+15, y : (yfScaledPosition+f_success_factor)};
						var bottomLeftPoint = {x : -15, y : (ymScaledPosition+m_success_factor)};

						var topLineEquation = straightEquation(topLeftPoint.x, topLeftPoint.y, topRightPoint.x, topRightPoint.y);
						var bottomLineEquation = straightEquation(bottomLeftPoint.x, bottomLeftPoint.y, bottomRightPoint.x, bottomRightPoint.y);

						var twoTierTopRightPoint = {x : 0.6*(w+15),  y : topLineEquation(0.6*(w+15))};
						var twoTierBottomRightPoint = {x : 0.6*(w+15), y : bottomLineEquation(0.6*(w+15))};


						var allPoints = [];
						allPoints.push(twoTierTopRightPoint.x +','+ twoTierTopRightPoint.y);
						allPoints.push(bottomRightPoint.x + ',' + bottomRightPoint.y);
						allPoints.push(twoTierBottomRightPoint.x + ',' + twoTierBottomRightPoint.y);
						allPoints.push(twoTierBottomRightPoint.x + ',' + twoTierBottomRightPoint.y);
					
						return allPoints.join(' ');
					})
					.attr("style",function(d){
						return commonStyleFunction(d, d.f_like_m);
					});
};

var straightEquation = function(x1,y1,x2,y2){
  var a = (y2-y1)/(x2-x1);
  var b = y2 - a*x2;
  return function(x){
     return a*x+b;
  };
};

var commonStyleFunction = function(d, factor){
	var strokeWidth="stroke-width:1";
	var stroke = "stroke:purple";

	var styles = [];
	styles.push(fillFunction(factor));
	styles.push(strokeWidth);
	styles.push(stroke);

	return styles.join(";");
};

//@TODO : Provide a better fill function
var fillFunction = function(factor){
	var fill;
	
	if(factor >= 0.6){
		fill = "fill:rgb(0,"+Math.round(factor*255)+",0)";
	} else if(factor > 0.3 && factor < 0.6){
		fill = "fill:rgb(0,"+Math.round(factor*255)+",0)";
	} else {
		fill = "fill:rgb("+Math.round((1-factor))*255+",0,0)";
	}

	return fill;
};
})();
