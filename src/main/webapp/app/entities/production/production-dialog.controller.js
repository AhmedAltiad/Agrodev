(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProductionDialogController', ProductionDialogController);

    ProductionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Production', 'Agriculteur', 'Variete'];

    function ProductionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Production, Agriculteur, Variete) {
        var vm = this;

        vm.production = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.agriculteurs = Agriculteur.query();
        vm.varietes = Variete.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.production.id !== null) {
                Production.update(vm.production, onSaveSuccess, onSaveError);
            } else {
                Production.save(vm.production, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:productionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateofproduction = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
