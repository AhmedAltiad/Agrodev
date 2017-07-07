(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PortefolioDetailController', PortefolioDetailController);

    PortefolioDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Portefolio', 'TraderAGB', 'Variete'];

    function PortefolioDetailController($scope, $rootScope, $stateParams, previousState, entity, Portefolio, TraderAGB, Variete) {
        var vm = this;

        vm.portefolio = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('agroBourse360SiApp:portefolioUpdate', function(event, result) {
            vm.portefolio = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
