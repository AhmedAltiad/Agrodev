(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('EmpCV', EmpCV);

    EmpCV.$inject = ['$resource'];

    function EmpCV ($resource) {
        var resourceUrl =  'api/emp-cvs/:id';

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
