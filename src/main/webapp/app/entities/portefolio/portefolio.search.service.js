(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('PortefolioSearch', PortefolioSearch);

    PortefolioSearch.$inject = ['$resource'];

    function PortefolioSearch($resource) {
        var resourceUrl =  'api/_search/portefolios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
