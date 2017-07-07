(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PalierDialogController', PalierDialogController);

    PalierDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Palier', 'Produit', 'Annonce'];

    function PalierDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Palier, Produit, Annonce) {
        var vm = this;

        vm.palier = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.produits = Produit.query();
        vm.annonces = Annonce.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.palier.id !== null) {
                Palier.update(vm.palier, onSaveSuccess, onSaveError);
            } else {
                Palier.save(vm.palier, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:palierUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.datedebut = false;
        vm.datePickerOpenStatus.datefin = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
