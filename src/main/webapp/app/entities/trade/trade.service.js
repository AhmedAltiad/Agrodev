(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Trade', Trade);

    Trade.$inject = ['$resource', 'DateUtils'];

    function Trade ($resource, DateUtils) {
        var resourceUrl =  'api/trades/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateOfOrder = DateUtils.convertLocalDateFromServer(data.dateOfOrder);
                        data.dateOfDelivery = DateUtils.convertLocalDateFromServer(data.dateOfDelivery);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateOfOrder = DateUtils.convertLocalDateToServer(copy.dateOfOrder);
                    copy.dateOfDelivery = DateUtils.convertLocalDateToServer(copy.dateOfDelivery);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateOfOrder = DateUtils.convertLocalDateToServer(copy.dateOfOrder);
                    copy.dateOfDelivery = DateUtils.convertLocalDateToServer(copy.dateOfDelivery);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
