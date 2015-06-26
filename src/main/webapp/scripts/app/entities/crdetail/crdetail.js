'use strict';

angular.module('jcrdashApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('crdetail', {
                parent: 'entity',
                url: '/crdetail',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jcrdashApp.crdetail.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/crdetail/crdetails.html',
                        controller: 'CrdetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('crdetail');
                        return $translate.refresh();
                    }]
                }
            })
            .state('crdetailDetail', {
                parent: 'entity',
                url: '/crdetail/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jcrdashApp.crdetail.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/crdetail/crdetail-detail.html',
                        controller: 'CrdetailDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('crdetail');
                        return $translate.refresh();
                    }]
                }
            });
    });
