(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('ProfilSearch', ProfilSearch);

    ProfilSearch.$inject = ['$resource'];

    function ProfilSearch($resource) {
        var resourceUrl =  'api/_search/profils/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
