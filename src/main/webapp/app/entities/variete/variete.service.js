(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Variete', Variete);

    Variete.$inject = ['$resource'];

    function Variete ($resource) {
        var resourceUrl =  'api/varietes/:id';

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
