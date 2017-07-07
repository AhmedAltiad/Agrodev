(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('EmpCVSearch', EmpCVSearch);

    EmpCVSearch.$inject = ['$resource'];

    function EmpCVSearch($resource) {
        var resourceUrl =  'api/_search/emp-cvs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
