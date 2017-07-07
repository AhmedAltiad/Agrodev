(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('TraderAGB', TraderAGB);

    TraderAGB.$inject = ['$resource'];

    function TraderAGB ($resource) {
        var resourceUrl =  'api/trader-agbs/:id';

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
