(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('TraderAGBSearch', TraderAGBSearch);

    TraderAGBSearch.$inject = ['$resource'];

    function TraderAGBSearch($resource) {
        var resourceUrl =  'api/_search/trader-agbs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
