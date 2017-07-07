(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('EmpActualiteSearch', EmpActualiteSearch);

    EmpActualiteSearch.$inject = ['$resource'];

    function EmpActualiteSearch($resource) {
        var resourceUrl =  'api/_search/emp-actualites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
