(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeHistoriqueDialogController', ECommandeHistoriqueDialogController);

    ECommandeHistoriqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ECommandeHistorique', 'AnnonceHistorique', 'Profil', 'TransactionHistorique'];

    function ECommandeHistoriqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ECommandeHistorique, AnnonceHistorique, Profil, TransactionHistorique) {
        var vm = this;

        vm.eCommandeHistorique = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.annoncehistoriques = AnnonceHistorique.query();
        vm.profils = Profil.query();
        vm.transactionhistoriques = TransactionHistorique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eCommandeHistorique.id !== null) {
                ECommandeHistorique.update(vm.eCommandeHistorique, onSaveSuccess, onSaveError);
            } else {
                ECommandeHistorique.save(vm.eCommandeHistorique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:eCommandeHistoriqueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
