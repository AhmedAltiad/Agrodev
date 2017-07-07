(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('AnnonceChangement', AnnonceChangement);

    AnnonceChangement.$inject = ['$resource', 'DateUtils'];

    function AnnonceChangement ($resource, DateUtils) {
        var resourceUrl =  'api/annonce-changements/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createddate = DateUtils.convertDateTimeFromServer(data.createddate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
