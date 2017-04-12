(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .controller('ProfilDetailController', ProfilDetailController);

    ProfilDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profil', 'Annonce'];

    function ProfilDetailController($scope, $rootScope, $stateParams, previousState, entity, Profil, Annonce) {
        var vm = this;

        vm.profil = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourseApp:profilUpdate', function(event, result) {
            vm.profil = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
