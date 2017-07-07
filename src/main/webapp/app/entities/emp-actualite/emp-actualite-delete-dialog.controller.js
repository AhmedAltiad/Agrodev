(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpActualiteDeleteController',EmpActualiteDeleteController);

    EmpActualiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmpActualite'];

    function EmpActualiteDeleteController($uibModalInstance, entity, EmpActualite) {
        var vm = this;

        vm.empActualite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmpActualite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
