'use strict';

angular.module('jcrdashApp')
    .controller('CrdetailDetailController', function ($scope, $stateParams, Crdetail) {
        $scope.crdetail = {};
        $scope.load = function (id) {
            Crdetail.get({id: id}, function(result) {
              $scope.crdetail = result;
            });
        };
        $scope.load($stateParams.id);
    });
