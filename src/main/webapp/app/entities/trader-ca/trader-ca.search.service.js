(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('TraderCASearch', TraderCASearch);

    TraderCASearch.$inject = ['$resource'];

    function TraderCASearch($resource) {
        var resourceUrl =  'api/_search/trader-cas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
