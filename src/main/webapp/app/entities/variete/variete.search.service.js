(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('VarieteSearch', VarieteSearch);

    VarieteSearch.$inject = ['$resource'];

    function VarieteSearch($resource) {
        var resourceUrl =  'api/_search/varietes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
