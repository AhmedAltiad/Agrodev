(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('CommandeDetails', CommandeDetails);

    CommandeDetails.$inject = ['$resource'];

    function CommandeDetails ($resource) {
        var resourceUrl =  'api/commande-details/:id';

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
