(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('AnnonceHistoriqueSearch', AnnonceHistoriqueSearch);

    AnnonceHistoriqueSearch.$inject = ['$resource'];

    function AnnonceHistoriqueSearch($resource) {
        var resourceUrl =  'api/_search/annonce-historiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
