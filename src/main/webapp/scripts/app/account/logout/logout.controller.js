'use strict';

angular.module('jcrdashApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
