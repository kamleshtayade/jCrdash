'use strict';

angular.module('jcrdashApp')
    .controller('CrdetailController', function ($scope, Crdetail, CrdetailSearch, ParseLinks) {
        $scope.crdetails = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Crdetail.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.crdetails = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Crdetail.get({id: id}, function(result) {
                $scope.crdetail = result;
                $('#saveCrdetailModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.crdetail.id != null) {
                Crdetail.update($scope.crdetail,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Crdetail.save($scope.crdetail,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Crdetail.get({id: id}, function(result) {
                $scope.crdetail = result;
                $('#deleteCrdetailConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Crdetail.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCrdetailConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            CrdetailSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.crdetails = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCrdetailModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.crdetail = {changeId: null, changeSummary: null, chSchdFiscalWeek: null, chSchdFiscalMth: null, ciName: null, ciPriority: null, dependentCiName: null, parentCiName: null, ciType: null, changeStatus: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
