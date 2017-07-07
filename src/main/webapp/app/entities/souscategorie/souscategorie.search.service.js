(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('SouscategorieSearch', SouscategorieSearch);

    SouscategorieSearch.$inject = ['$resource'];

    function SouscategorieSearch($resource) {
        var resourceUrl =  'api/_search/souscategories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
