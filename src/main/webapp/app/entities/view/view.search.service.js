(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('ViewSearch', ViewSearch);

    ViewSearch.$inject = ['$resource'];

    function ViewSearch($resource) {
        var resourceUrl =  'api/_search/views/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
