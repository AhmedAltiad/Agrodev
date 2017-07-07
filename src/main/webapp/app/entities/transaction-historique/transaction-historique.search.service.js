(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('TransactionHistoriqueSearch', TransactionHistoriqueSearch);

    TransactionHistoriqueSearch.$inject = ['$resource'];

    function TransactionHistoriqueSearch($resource) {
        var resourceUrl =  'api/_search/transaction-historiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
