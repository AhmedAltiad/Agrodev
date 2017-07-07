(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('TraderCA', TraderCA);

    TraderCA.$inject = ['$resource'];

    function TraderCA ($resource) {
        var resourceUrl =  'api/trader-cas/:id';

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
