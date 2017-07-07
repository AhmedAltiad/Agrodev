(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ViewDetailController', ViewDetailController);

    ViewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'View', 'Profil', 'AnnonceHistorique'];

    function ViewDetailController($scope, $rootScope, $stateParams, previousState, entity, View, Profil, AnnonceHistorique) {
        var vm = this;

        vm.view = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:viewUpdate', function(event, result) {
            vm.view = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
