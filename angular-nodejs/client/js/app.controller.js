angular
    .module("speed-data-app")
    .controller("baseController", ['$scope', 'vizualizeD3', '$http', function($scope, vizualizeD3, $http) {
        $scope
            .$watch('mAttribute', function(newValue, oldValue){
            changeAttributeFunc(newValue, oldValue);
        });

        $scope
            .$watch('wAttribute', function(newValue, oldValue){
            changeAttributeFunc(newValue, oldValue);
        });

        var changeAttributeFunc = function(newValue, oldValue){
            if(newValue != oldValue && $scope.mAttribute !== undefined && $scope.wAttribute !== undefined){
                $http.get('data/'+$scope.mAttribute.normalized_attribute_name+'_'+$scope.wAttribute.normalized_attribute_name+'.json')
                .success(function(data, status, headers, config){
                    d3.select("#graph g").remove();
                    vizualizeD3(data);
                });
            }
        };

       $http.get('data/attributes.json')
        .success(function(data, status, headers, config){
            $scope.attributes = data;
        });
    }]);
