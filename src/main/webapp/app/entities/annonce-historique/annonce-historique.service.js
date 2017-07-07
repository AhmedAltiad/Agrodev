(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('AnnonceHistorique', AnnonceHistorique);

    AnnonceHistorique.$inject = ['$resource', 'DateUtils'];

    function AnnonceHistorique ($resource, DateUtils) {
        var resourceUrl =  'api/annonce-historiques/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createddate = DateUtils.convertDateTimeFromServer(data.createddate);
                        data.lastmodifieddate = DateUtils.convertDateTimeFromServer(data.lastmodifieddate);
                        data.dateactivation = DateUtils.convertLocalDateFromServer(data.dateactivation);
                        data.dateexpiration = DateUtils.convertLocalDateFromServer(data.dateexpiration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateactivation = DateUtils.convertLocalDateToServer(copy.dateactivation);
                    copy.dateexpiration = DateUtils.convertLocalDateToServer(copy.dateexpiration);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateactivation = DateUtils.convertLocalDateToServer(copy.dateactivation);
                    copy.dateexpiration = DateUtils.convertLocalDateToServer(copy.dateexpiration);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
