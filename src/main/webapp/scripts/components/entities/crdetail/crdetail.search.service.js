'use strict';

angular.module('jcrdashApp')
    .factory('CrdetailSearch', function ($resource) {
        return $resource('api/_search/crdetails/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
