(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeDialogController', CommandeDialogController);

    CommandeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Commande', 'CommandeDetails', 'Currency', 'TraderAGB', 'Agriculteur'];

    function CommandeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Commande, CommandeDetails, Currency, TraderAGB, Agriculteur) {
        var vm = this;

        vm.commande = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.commandedetails = CommandeDetails.query();
        vm.currencies = Currency.query();
        vm.traderagbs = TraderAGB.query();
        vm.agriculteurs = Agriculteur.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.commande.id !== null) {
                Commande.update(vm.commande, onSaveSuccess, onSaveError);
            } else {
                Commande.save(vm.commande, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:commandeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateOfOrder = false;
        vm.datePickerOpenStatus.dateOfDelivery = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
