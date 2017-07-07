(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('SpecialiteAgricole', SpecialiteAgricole);

    SpecialiteAgricole.$inject = ['$resource'];

    function SpecialiteAgricole ($resource) {
        var resourceUrl =  'api/specialite-agricoles/:id';

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
