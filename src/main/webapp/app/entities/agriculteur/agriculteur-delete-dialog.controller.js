(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AgriculteurDeleteController',AgriculteurDeleteController);

    AgriculteurDeleteController.$inject = ['$uibModalInstance', 'entity', 'Agriculteur'];

    function AgriculteurDeleteController($uibModalInstance, entity, Agriculteur) {
        var vm = this;

        vm.agriculteur = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Agriculteur.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
