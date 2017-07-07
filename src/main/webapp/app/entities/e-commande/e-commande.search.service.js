(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('ECommandeSearch', ECommandeSearch);

    ECommandeSearch.$inject = ['$resource'];

    function ECommandeSearch($resource) {
        var resourceUrl =  'api/_search/e-commandes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
