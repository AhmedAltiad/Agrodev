(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Agriculteur', Agriculteur);

    Agriculteur.$inject = ['$resource'];

    function Agriculteur ($resource) {
        var resourceUrl =  'api/agriculteurs/:id';

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
