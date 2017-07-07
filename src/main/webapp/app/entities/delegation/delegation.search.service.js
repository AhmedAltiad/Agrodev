(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('DelegationSearch', DelegationSearch);

    DelegationSearch.$inject = ['$resource'];

    function DelegationSearch($resource) {
        var resourceUrl =  'api/_search/delegations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
