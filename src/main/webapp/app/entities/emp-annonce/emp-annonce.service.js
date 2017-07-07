(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('EmpAnnonce', EmpAnnonce);

    EmpAnnonce.$inject = ['$resource', 'DateUtils'];

    function EmpAnnonce ($resource, DateUtils) {
        var resourceUrl =  'api/emp-annonces/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.initDate = DateUtils.convertLocalDateFromServer(data.initDate);
                        data.finDate = DateUtils.convertLocalDateFromServer(data.finDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.initDate = DateUtils.convertLocalDateToServer(copy.initDate);
                    copy.finDate = DateUtils.convertLocalDateToServer(copy.finDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.initDate = DateUtils.convertLocalDateToServer(copy.initDate);
                    copy.finDate = DateUtils.convertLocalDateToServer(copy.finDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
