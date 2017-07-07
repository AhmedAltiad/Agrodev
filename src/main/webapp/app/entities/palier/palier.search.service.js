(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('PalierSearch', PalierSearch);

    PalierSearch.$inject = ['$resource'];

    function PalierSearch($resource) {
        var resourceUrl =  'api/_search/paliers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
