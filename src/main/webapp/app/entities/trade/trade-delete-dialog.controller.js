(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TradeDeleteController',TradeDeleteController);

    TradeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Trade'];

    function TradeDeleteController($uibModalInstance, entity, Trade) {
        var vm = this;

        vm.trade = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Trade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
