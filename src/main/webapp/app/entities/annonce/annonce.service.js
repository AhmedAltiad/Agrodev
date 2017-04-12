(function() {
    'use strict';
    angular
        .module('agroBourseApp')
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
                        data.datedebut = DateUtils.convertLocalDateFromServer(data.datedebut);
                        data.datefin = DateUtils.convertLocalDateFromServer(data.datefin);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datedebut = DateUtils.convertLocalDateToServer(copy.datedebut);
                    copy.datefin = DateUtils.convertLocalDateToServer(copy.datefin);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.datedebut = DateUtils.convertLocalDateToServer(copy.datedebut);
                    copy.datefin = DateUtils.convertLocalDateToServer(copy.datefin);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
