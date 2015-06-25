'use strict';

angular.module('jcrdashApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


