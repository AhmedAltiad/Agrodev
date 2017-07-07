(function() {
    'use strict';
    angular
        .module('agroBourse360SiApp')
        .factory('Portefolio', Portefolio);

    Portefolio.$inject = ['$resource'];

    function Portefolio ($resource) {
        var resourceUrl =  'api/portefolios/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
