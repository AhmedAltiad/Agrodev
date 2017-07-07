(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('GPOSearch', GPOSearch);

    GPOSearch.$inject = ['$resource'];

    function GPOSearch($resource) {
        var resourceUrl =  'api/_search/g-pos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
