(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('View', View);

    View.$inject = ['$resource', 'DateUtils'];

    function View ($resource, DateUtils) {
        var resourceUrl =  'api/views/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.duration = DateUtils.convertLocalDateFromServer(data.duration);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.duration = DateUtils.convertLocalDateToServer(copy.duration);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.duration = DateUtils.convertLocalDateToServer(copy.duration);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
