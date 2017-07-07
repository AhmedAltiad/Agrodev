(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('ECommandeHistorique', ECommandeHistorique);

    ECommandeHistorique.$inject = ['$resource', 'DateUtils'];

    function ECommandeHistorique ($resource, DateUtils) {
        var resourceUrl =  'api/e-commande-historiques/:id';

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
