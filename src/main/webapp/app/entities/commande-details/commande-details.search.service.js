(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('CommandeDetailsSearch', CommandeDetailsSearch);

    CommandeDetailsSearch.$inject = ['$resource'];

    function CommandeDetailsSearch($resource) {
        var resourceUrl =  'api/_search/commande-details/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
