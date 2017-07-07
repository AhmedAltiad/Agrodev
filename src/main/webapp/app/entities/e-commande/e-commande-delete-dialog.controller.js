(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeDeleteController',ECommandeDeleteController);

    ECommandeDeleteController.$inject = ['$uibModalInstance', 'entity', 'ECommande'];

    function ECommandeDeleteController($uibModalInstance, entity, ECommande) {
        var vm = this;

        vm.eCommande = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ECommande.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
