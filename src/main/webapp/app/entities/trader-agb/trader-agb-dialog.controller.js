(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderAGBDialogController', TraderAGBDialogController);

    TraderAGBDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TraderAGB', 'Profil', 'Desk', 'Commande', 'Trade', 'Portefolio'];

    function TraderAGBDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TraderAGB, Profil, Desk, Commande, Trade, Portefolio) {
        var vm = this;

        vm.traderAGB = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profils = Profil.query({filter: 'traderagb-is-null'});
        $q.all([vm.traderAGB.$promise, vm.profils.$promise]).then(function() {
            if (!vm.traderAGB.profil || !vm.traderAGB.profil.id) {
                return $q.reject();
            }
            return Profil.get({id : vm.traderAGB.profil.id}).$promise;
        }).then(function(profil) {
            vm.profils.push(profil);
        });
        vm.desks = Desk.query();
        vm.commandes = Commande.query();
        vm.trades = Trade.query();
        vm.portefolios = Portefolio.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traderAGB.id !== null) {
                TraderAGB.update(vm.traderAGB, onSaveSuccess, onSaveError);
            } else {
                TraderAGB.save(vm.traderAGB, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:traderAGBUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
