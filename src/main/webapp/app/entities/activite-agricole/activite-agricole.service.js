(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('ActiviteAgricole', ActiviteAgricole);

    ActiviteAgricole.$inject = ['$resource'];

    function ActiviteAgricole ($resource) {
        var resourceUrl =  'api/activite-agricoles/:id';

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
