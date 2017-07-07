(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TransactionHistoriqueDeleteController',TransactionHistoriqueDeleteController);

    TransactionHistoriqueDeleteController.$inject = ['$uibModalInstance', 'entity', 'TransactionHistorique'];

    function TransactionHistoriqueDeleteController($uibModalInstance, entity, TransactionHistorique) {
        var vm = this;

        vm.transactionHistorique = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TransactionHistorique.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
