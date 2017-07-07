(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Profil', Profil);

    Profil.$inject = ['$resource'];

    function Profil ($resource) {
        var resourceUrl =  'api/profils/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
