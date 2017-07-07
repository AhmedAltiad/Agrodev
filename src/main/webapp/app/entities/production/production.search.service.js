(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('ProductionSearch', ProductionSearch);

    ProductionSearch.$inject = ['$resource'];

    function ProductionSearch($resource) {
        var resourceUrl =  'api/_search/productions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
