(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Annonce', Annonce);

    Annonce.$inject = ['$resource', 'DateUtils'];

    function Annonce ($resource, DateUtils) {
        var resourceUrl =  'api/annonces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createddate = DateUtils.convertDateTimeFromServer(data.createddate);
                        data.lastmodifieddate = DateUtils.convertDateTimeFromServer(data.lastmodifieddate);
                        data.dateActivation = DateUtils.convertLocalDateFromServer(data.dateActivation);
                        data.dateExpiration = DateUtils.convertLocalDateFromServer(data.dateExpiration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateActivation = DateUtils.convertLocalDateToServer(copy.dateActivation);
                    copy.dateExpiration = DateUtils.convertLocalDateToServer(copy.dateExpiration);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateActivation = DateUtils.convertLocalDateToServer(copy.dateActivation);
                    copy.dateExpiration = DateUtils.convertLocalDateToServer(copy.dateExpiration);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
