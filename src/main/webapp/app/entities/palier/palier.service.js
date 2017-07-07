(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Palier', Palier);

    Palier.$inject = ['$resource', 'DateUtils'];

    function Palier ($resource, DateUtils) {
        var resourceUrl =  'api/paliers/:id';

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
