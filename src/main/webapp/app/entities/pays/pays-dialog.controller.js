(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PaysDialogController', PaysDialogController);

    PaysDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Pays', 'Region'];

    function PaysDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Pays, Region) {
        var vm = this;

        vm.pays = entity;
        vm.clear = clear;
        vm.save = save;
        vm.regions = Region.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pays.id !== null) {
                Pays.update(vm.pays, onSaveSuccess, onSaveError);
            } else {
                Pays.save(vm.pays, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:paysUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
