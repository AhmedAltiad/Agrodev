(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('AnnonceChangementSearch', AnnonceChangementSearch);

    AnnonceChangementSearch.$inject = ['$resource'];

    function AnnonceChangementSearch($resource) {
        var resourceUrl =  'api/_search/annonce-changements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
