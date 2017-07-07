(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeHistoriqueDeleteController',ECommandeHistoriqueDeleteController);

    ECommandeHistoriqueDeleteController.$inject = ['$uibModalInstance', 'entity', 'ECommandeHistorique'];

    function ECommandeHistoriqueDeleteController($uibModalInstance, entity, ECommandeHistorique) {
        var vm = this;

        vm.eCommandeHistorique = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ECommandeHistorique.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
