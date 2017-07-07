(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .factory('CvSpecialitiesSearch', CvSpecialitiesSearch);

    CvSpecialitiesSearch.$inject = ['$resource'];

    function CvSpecialitiesSearch($resource) {
        var resourceUrl =  'api/_search/cv-specialities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
