(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('TradeSearch', TradeSearch);

    TradeSearch.$inject = ['$resource'];

    function TradeSearch($resource) {
        var resourceUrl =  'api/_search/trades/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
