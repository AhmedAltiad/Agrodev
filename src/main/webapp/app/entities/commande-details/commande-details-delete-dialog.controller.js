(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeDetailsDeleteController',CommandeDetailsDeleteController);

    CommandeDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'CommandeDetails'];

    function CommandeDetailsDeleteController($uibModalInstance, entity, CommandeDetails) {
        var vm = this;

        vm.commandeDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CommandeDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
