(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SpecialiteAgricoleDialogController', SpecialiteAgricoleDialogController);

    SpecialiteAgricoleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SpecialiteAgricole', 'Agriculteur'];

    function SpecialiteAgricoleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SpecialiteAgricole, Agriculteur) {
        var vm = this;

        vm.specialiteAgricole = entity;
        vm.clear = clear;
        vm.save = save;
        vm.agriculteurs = Agriculteur.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.specialiteAgricole.id !== null) {
                SpecialiteAgricole.update(vm.specialiteAgricole, onSaveSuccess, onSaveError);
            } else {
                SpecialiteAgricole.save(vm.specialiteAgricole, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:specialiteAgricoleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
