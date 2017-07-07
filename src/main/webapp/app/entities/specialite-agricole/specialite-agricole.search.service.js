(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('SpecialiteAgricoleSearch', SpecialiteAgricoleSearch);

    SpecialiteAgricoleSearch.$inject = ['$resource'];

    function SpecialiteAgricoleSearch($resource) {
        var resourceUrl =  'api/_search/specialite-agricoles/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
