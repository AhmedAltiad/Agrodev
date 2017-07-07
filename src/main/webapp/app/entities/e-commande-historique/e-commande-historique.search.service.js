(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('ECommandeHistoriqueSearch', ECommandeHistoriqueSearch);

    ECommandeHistoriqueSearch.$inject = ['$resource'];

    function ECommandeHistoriqueSearch($resource) {
        var resourceUrl =  'api/_search/e-commande-historiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
