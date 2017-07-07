(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('AgriculteurSearch', AgriculteurSearch);

    AgriculteurSearch.$inject = ['$resource'];

    function AgriculteurSearch($resource) {
        var resourceUrl =  'api/_search/agriculteurs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
