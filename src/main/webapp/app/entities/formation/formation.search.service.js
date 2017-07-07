(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('FormationSearch', FormationSearch);

    FormationSearch.$inject = ['$resource'];

    function FormationSearch($resource) {
        var resourceUrl =  'api/_search/formations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
