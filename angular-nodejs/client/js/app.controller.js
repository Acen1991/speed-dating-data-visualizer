angular.module("speed-data-app")
    .controller("baseController", ['$scope', 'Vizualize', '$http', function($scope, Vizualize, $http) {

        $scope.$watchCollection(function() {
            return [$scope.mAttribute, $scope.wAttribute];
        }, function() {
            if ($scope.mAttribute !== undefined && $scope.wAttribute !== undefined) {
                $http.get('data/' + $scope.mAttribute.normalized_attribute_name + '_' + $scope.wAttribute.normalized_attribute_name + '.json')
                    .success(function(data) {
                        d3.select("#graph g").remove();
                        Vizualize(data);
                    });
            }
        });

        $http.get('data/attributes.json')
            .success(function(data) {
                $scope.attributes = data;
            });
    }]);
