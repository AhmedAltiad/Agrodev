(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('PaysSearch', PaysSearch);

    PaysSearch.$inject = ['$resource'];

    function PaysSearch($resource) {
        var resourceUrl =  'api/_search/pays/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
