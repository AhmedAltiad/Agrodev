(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TransactionHistoriqueDialogController', TransactionHistoriqueDialogController);

    TransactionHistoriqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TransactionHistorique', 'ECommandeHistorique'];

    function TransactionHistoriqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TransactionHistorique, ECommandeHistorique) {
        var vm = this;

        vm.transactionHistorique = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.ecommandehistoriques = ECommandeHistorique.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.transactionHistorique.id !== null) {
                TransactionHistorique.update(vm.transactionHistorique, onSaveSuccess, onSaveError);
            } else {
                TransactionHistorique.save(vm.transactionHistorique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:transactionHistoriqueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;
        vm.datePickerOpenStatus.dateValidation = false;
        vm.datePickerOpenStatus.dateValidationPaiment = false;
        vm.datePickerOpenStatus.dateRefuse = false;
        vm.datePickerOpenStatus.dateRefuspaiment = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
