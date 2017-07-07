(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployerDeleteController',EmployerDeleteController);

    EmployerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Employer'];

    function EmployerDeleteController($uibModalInstance, entity, Employer) {
        var vm = this;

        vm.employer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Employer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
