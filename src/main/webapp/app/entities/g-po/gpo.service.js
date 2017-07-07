(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('GPO', GPO);

    GPO.$inject = ['$resource', 'DateUtils'];

    function GPO ($resource, DateUtils) {
        var resourceUrl =  'api/g-pos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCreationEntreprise = DateUtils.convertLocalDateFromServer(data.dateCreationEntreprise);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateCreationEntreprise = DateUtils.convertLocalDateToServer(copy.dateCreationEntreprise);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateCreationEntreprise = DateUtils.convertLocalDateToServer(copy.dateCreationEntreprise);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
