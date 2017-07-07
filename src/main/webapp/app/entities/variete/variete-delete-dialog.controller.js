(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('VarieteDeleteController',VarieteDeleteController);

    VarieteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Variete'];

    function VarieteDeleteController($uibModalInstance, entity, Variete) {
        var vm = this;

        vm.variete = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Variete.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
