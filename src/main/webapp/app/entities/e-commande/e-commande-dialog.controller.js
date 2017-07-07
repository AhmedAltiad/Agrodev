(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeDialogController', ECommandeDialogController);

    ECommandeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ECommande', 'Annonce', 'Profil', 'Transaction'];

    function ECommandeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ECommande, Annonce, Profil, Transaction) {
        var vm = this;

        vm.eCommande = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.annonces = Annonce.query();
        vm.profils = Profil.query();
        vm.transactions = Transaction.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.eCommande.id !== null) {
                ECommande.update(vm.eCommande, onSaveSuccess, onSaveError);
            } else {
                ECommande.save(vm.eCommande, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:eCommandeUpdate', result);
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
