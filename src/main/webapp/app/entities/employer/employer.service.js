(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Employer', Employer);

    Employer.$inject = ['$resource'];

    function Employer ($resource) {
        var resourceUrl =  'api/employers/:id';

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
