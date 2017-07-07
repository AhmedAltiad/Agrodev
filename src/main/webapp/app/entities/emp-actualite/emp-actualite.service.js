(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('EmpActualite', EmpActualite);

    EmpActualite.$inject = ['$resource', 'DateUtils'];

    function EmpActualite ($resource, DateUtils) {
        var resourceUrl =  'api/emp-actualites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.initDate = DateUtils.convertLocalDateFromServer(data.initDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.initDate = DateUtils.convertLocalDateToServer(copy.initDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.initDate = DateUtils.convertLocalDateToServer(copy.initDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
