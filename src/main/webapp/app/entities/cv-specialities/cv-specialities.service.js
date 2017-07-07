(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('CvSpecialities', CvSpecialities);

    CvSpecialities.$inject = ['$resource'];

    function CvSpecialities ($resource) {
        var resourceUrl =  'api/cv-specialities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
