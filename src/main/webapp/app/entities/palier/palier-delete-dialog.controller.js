(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PalierDeleteController',PalierDeleteController);

    PalierDeleteController.$inject = ['$uibModalInstance', 'entity', 'Palier'];

    function PalierDeleteController($uibModalInstance, entity, Palier) {
        var vm = this;

        vm.palier = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Palier.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
