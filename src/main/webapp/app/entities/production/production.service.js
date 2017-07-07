(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Production', Production);

    Production.$inject = ['$resource', 'DateUtils'];

    function Production ($resource, DateUtils) {
        var resourceUrl =  'api/productions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateofproduction = DateUtils.convertLocalDateFromServer(data.dateofproduction);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateofproduction = DateUtils.convertLocalDateToServer(copy.dateofproduction);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateofproduction = DateUtils.convertLocalDateToServer(copy.dateofproduction);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
