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
        "m_axis_values": ["lawyer", "doctor", "engineer", "artist"],
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

var ym_range = d3.scale.ordinal().domain(data.common.m_axis_values).rangePoints([0, 400]);
//for example to know where is the position of "engineer" on the axis we need to call ym_range.call('scale','engineer')
yf_range = d3.scale.ordinal().domain(data.common.f_axis_values).rangePoints([0, 400]);
//for example to know where is the position of    "2K"    on the axis we need to call yf_range.call('scale','2K')

// Add an SVG element with the desired dimensions and margin.
var graph = d3.select("#graph").append("svg:svg")
    .attr("width", w + m[1] + m[3])
    .attr("height", h + m[0] + m[2])
    .append("svg:g")
    .attr("transform", "translate(" + m[3] + "," + m[0] + ")");

// create left yAxis
var ymAxis = d3.svg.axis().scale(ym_range).ticks(4).orient("left");
// Add the y-axis to the left
graph.append("svg:g")
    .attr("class", "y axis axisLeft")
    .attr("transform", "translate(-15,0)")
    .call(ymAxis);

// create right yAxis
var yfRight = d3.svg.axis().scale(yf_range).ticks(6).orient("right");
// Add the y-axis to the right
graph.append("svg:g")
    .attr("class", "y axis axisRight")
    .attr("transform", "translate(" + (w + 15) + ",0)")
    .call(yfRight);