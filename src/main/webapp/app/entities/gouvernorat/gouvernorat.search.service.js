(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('GouvernoratSearch', GouvernoratSearch);

    GouvernoratSearch.$inject = ['$resource'];

    function GouvernoratSearch($resource) {
        var resourceUrl =  'api/_search/gouvernorats/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
