(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Souscategorie', Souscategorie);

    Souscategorie.$inject = ['$resource'];

    function Souscategorie ($resource) {
        var resourceUrl =  'api/souscategories/:id';

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
