(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('ActiviteAgricoleSearch', ActiviteAgricoleSearch);

    ActiviteAgricoleSearch.$inject = ['$resource'];

    function ActiviteAgricoleSearch($resource) {
        var resourceUrl =  'api/_search/activite-agricoles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
