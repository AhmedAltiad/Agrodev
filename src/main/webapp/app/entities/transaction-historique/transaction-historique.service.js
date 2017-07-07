(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('TransactionHistorique', TransactionHistorique);

    TransactionHistorique.$inject = ['$resource', 'DateUtils'];

    function TransactionHistorique ($resource, DateUtils) {
        var resourceUrl =  'api/transaction-historiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.date = DateUtils.convertDateTimeFromServer(data.date);
                        data.dateValidation = DateUtils.convertDateTimeFromServer(data.dateValidation);
                        data.dateValidationPaiment = DateUtils.convertDateTimeFromServer(data.dateValidationPaiment);
                        data.dateRefuse = DateUtils.convertDateTimeFromServer(data.dateRefuse);
                        data.dateRefuspaiment = DateUtils.convertDateTimeFromServer(data.dateRefuspaiment);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
