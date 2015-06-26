'use strict';

angular.module('jcrdashApp')
    .factory('Crdetail', function ($resource, DateUtils) {
        return $resource('api/crdetails/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
