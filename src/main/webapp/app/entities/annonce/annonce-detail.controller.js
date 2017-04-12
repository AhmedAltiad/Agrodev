(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .controller('AnnonceDetailController', AnnonceDetailController);

    AnnonceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Annonce', 'Profil'];

    function AnnonceDetailController($scope, $rootScope, $stateParams, previousState, entity, Annonce, Profil) {
        var vm = this;

        vm.annonce = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourseApp:annonceUpdate', function(event, result) {
            vm.annonce = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
