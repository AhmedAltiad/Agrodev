(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('EmployerSearch', EmployerSearch);

    EmployerSearch.$inject = ['$resource'];

    function EmployerSearch($resource) {
        var resourceUrl =  'api/_search/employers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
