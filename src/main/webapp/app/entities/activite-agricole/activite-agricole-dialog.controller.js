(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ActiviteAgricoleDialogController', ActiviteAgricoleDialogController);

    ActiviteAgricoleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ActiviteAgricole', 'Agriculteur'];

    function ActiviteAgricoleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ActiviteAgricole, Agriculteur) {
        var vm = this;

        vm.activiteAgricole = entity;
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
            if (vm.activiteAgricole.id !== null) {
                ActiviteAgricole.update(vm.activiteAgricole, onSaveSuccess, onSaveError);
            } else {
                ActiviteAgricole.save(vm.activiteAgricole, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:activiteAgricoleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
