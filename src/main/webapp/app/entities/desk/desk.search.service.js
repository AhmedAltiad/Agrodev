(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('DeskSearch', DeskSearch);

    DeskSearch.$inject = ['$resource'];

    function DeskSearch($resource) {
        var resourceUrl =  'api/_search/desks/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
