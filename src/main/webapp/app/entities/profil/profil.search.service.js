(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .factory('ProfilSearch', ProfilSearch);

    ProfilSearch.$inject = ['$resource'];

    function ProfilSearch($resource) {
        var resourceUrl =  'api/_search/profils/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
