(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .factory('AnnonceSearch', AnnonceSearch);

    AnnonceSearch.$inject = ['$resource'];

    function AnnonceSearch($resource) {
        var resourceUrl =  'api/_search/annonces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
