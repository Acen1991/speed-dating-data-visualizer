angular.module("speed-data-app", []).controller("baseController", ['$scope', function($scope) {}]);

/* implementation heavily influenced by http://bl.ocks.org/1166403 */
/* some arguments AGAINST the use of dual-scaled axes line graphs can be found at http://www.perceptualedge.com/articles/visual_business_intelligence/dual-scaled_axes.pdf */

// define dimensions of graph
var m = [80, 80, 80, 80]; // margins
var w = 1200 - m[1] - m[3]; // width
var h = 500 - m[0] - m[2]; // height

//Replace this with a backend call
var data = {
    "common": {
        "m_attribute": "job",
        "f_attribute": " wage",
        "m_axis_values": ["doctor", "engineer", "artist"],
        "f_axis_values": ["1K", "2K", "3K", "4K", "5K", "6K"]
    },
    "data": [{
        "m_attribute_value": "doctor",
        "f_attribute_value": "5K",
        "m_success_attribute": 0.7,
        "f_success_attribute": 0.5,
        "m_like_f": 0.6,
        "f_like_m": 0.3,
        "both_like": 0.5,
        "accuracy": 0.1
    }]
};


var y1 = d3.scale.ordinal().domain(data.common.m_axis_values).rangePoints([0, 400]);
var y2 = d3.scale.ordinal().domain(data.common.f_axis_values).rangePoints([0, 400]);
// var y = d3.scale.linear().domain([0, d3.max(data)]).range([h, 0]);

// Add an SVG element with the desired dimensions and margin.
var graph = d3.select("#graph").append("svg:svg")
    .attr("width", w + m[1] + m[3])
    .attr("height", h + m[0] + m[2])
    .append("svg:g")
    .attr("transform", "translate(" + m[3] + "," + m[0] + ")");

// create left yAxis
var yAxisLeft = d3.svg.axis().scale(y1).ticks(4).orient("left");
// Add the y-axis to the left
graph.append("svg:g")
    .attr("class", "y axis axisLeft")
    .attr("transform", "translate(-15,0)")
    .call(yAxisLeft);

// create right yAxis
var yAxisRight = d3.svg.axis().scale(y2).ticks(6).orient("right");
// Add the y-axis to the right
graph.append("svg:g")
    .attr("class", "y axis axisRight")
    .attr("transform", "translate(" + (w + 15) + ",0)")
    .call(yAxisRight);