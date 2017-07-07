(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('LocaliteSearch', LocaliteSearch);

    LocaliteSearch.$inject = ['$resource'];

    function LocaliteSearch($resource) {
        var resourceUrl =  'api/_search/localites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
