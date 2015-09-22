angular.module('speed-data-app').factory('Utils', [function() {
    var commonPolygonDataFunc = function(d, ym_range, yf_range, leftSide, rightSide) {
        var ymScaledPosition = ym_range.call('scale', d.m_attribute_value);

        var yfScaledPosition = yf_range.call('scale', d.f_attribute_value);
        var m_success_factor = d.m_success_attribute * 25;
        var f_success_factor = d.f_success_attribute * 25;

        var topLeftPoint = {
            x: leftSide,
            y: (ymScaledPosition - m_success_factor)
        };
        var topRightPoint = {
            x: rightSide,
            y: (yfScaledPosition - f_success_factor)
        };
        var bottomRightPoint = {
            x: rightSide,
            y: (yfScaledPosition + f_success_factor)
        };
        var bottomLeftPoint = {
            x: leftSide,
            y: (ymScaledPosition + m_success_factor)
        };

        var topLineEquation = straightEquation(topLeftPoint.x, topLeftPoint.y, topRightPoint.x, topRightPoint.y);
        var bottomLineEquation = straightEquation(bottomLeftPoint.x, bottomLeftPoint.y, bottomRightPoint.x, bottomRightPoint.y);

        return {
            borderPoints: {
                topLeftPoint: topLeftPoint,
                topRightPoint: topRightPoint,
                bottomRightPoint: bottomRightPoint,
                bottomLeftPoint: bottomLeftPoint
            },
            functions: {
                topLineEquation: topLineEquation,
                bottomLineEquation: bottomLineEquation
            }
        };
    };

    var straightEquation = function(x1, y1, x2, y2) {
        var a = (y2 - y1) / (x2 - x1);
        var b = y2 - a * x2;
        return function(x) {
            return a * x + b;
        };
    };

    var commonStyleFunction = function(d, factor) {
        var strokeWidth = "stroke-width:1";
        var stroke = "stroke:rgb(10,1(2/3)0)";

        var styles = [];
        styles.push(fillFunction(factor));
        styles.push(strokeWidth);
        styles.push(stroke);

        return styles.join(";");
    };

    //@TODO : Provide a better fill function
    var fillFunction = function(factor) {
        var fill;

        if (factor >= (2 / 3)) {
            fill = "fill:rgb(0," + Math.round(factor * 255) + ",0)";
        } else if (factor > (1 / 3) && factor < (2 / 3)) {
            fill = "fill:rgb(0," + Math.round(factor * 255) + ",0)";
        } else {
            fill = "fill:rgb(" + Math.round((1 - factor)) * 255 + ",0,0)";
        }

        return fill;
    };

    //Exported functions
    return {
        commonPolygonDataFunc: commonPolygonDataFunc,
        commonStyleFunction: commonStyleFunction,
    };
}]);
