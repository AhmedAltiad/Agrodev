(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ActiviteAgricoleDeleteController',ActiviteAgricoleDeleteController);

    ActiviteAgricoleDeleteController.$inject = ['$uibModalInstance', 'entity', 'ActiviteAgricole'];

    function ActiviteAgricoleDeleteController($uibModalInstance, entity, ActiviteAgricole) {
        var vm = this;

        vm.activiteAgricole = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ActiviteAgricole.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
