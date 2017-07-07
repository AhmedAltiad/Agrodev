(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Commande', Commande);

    Commande.$inject = ['$resource', 'DateUtils'];

    function Commande ($resource, DateUtils) {
        var resourceUrl =  'api/commandes/:id';

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
