(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpCVDeleteController',EmpCVDeleteController);

    EmpCVDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmpCV'];

    function EmpCVDeleteController($uibModalInstance, entity, EmpCV) {
        var vm = this;

        vm.empCV = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmpCV.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
