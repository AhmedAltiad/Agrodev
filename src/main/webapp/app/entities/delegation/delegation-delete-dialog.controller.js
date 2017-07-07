(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('DelegationDeleteController',DelegationDeleteController);

    DelegationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Delegation'];

    function DelegationDeleteController($uibModalInstance, entity, Delegation) {
        var vm = this;

        vm.delegation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Delegation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
