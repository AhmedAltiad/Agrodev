(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Gouvernorat', Gouvernorat);

    Gouvernorat.$inject = ['$resource'];

    function Gouvernorat ($resource) {
        var resourceUrl =  'api/gouvernorats/:id';

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
