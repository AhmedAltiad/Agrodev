(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Delegation', Delegation);

    Delegation.$inject = ['$resource'];

    function Delegation ($resource) {
        var resourceUrl =  'api/delegations/:id';

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
