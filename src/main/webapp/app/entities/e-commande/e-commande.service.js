(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('ECommande', ECommande);

    ECommande.$inject = ['$resource', 'DateUtils'];

    function ECommande ($resource, DateUtils) {
        var resourceUrl =  'api/e-commandes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
