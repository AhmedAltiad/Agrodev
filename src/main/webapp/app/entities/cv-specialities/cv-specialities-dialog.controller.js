(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CvSpecialitiesDialogController', CvSpecialitiesDialogController);

    CvSpecialitiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CvSpecialities', 'EmpCV'];

    function CvSpecialitiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CvSpecialities, EmpCV) {
        var vm = this;

        vm.cvSpecialities = entity;
        vm.clear = clear;
        vm.save = save;
        vm.empcvs = EmpCV.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.cvSpecialities.id !== null) {
                CvSpecialities.update(vm.cvSpecialities, onSaveSuccess, onSaveError);
            } else {
                CvSpecialities.save(vm.cvSpecialities, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:cvSpecialitiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
