(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('EmpAnnonceSearch', EmpAnnonceSearch);

    EmpAnnonceSearch.$inject = ['$resource'];

    function EmpAnnonceSearch($resource) {
        var resourceUrl =  'api/_search/emp-annonces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
