(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderCADeleteController',TraderCADeleteController);

    TraderCADeleteController.$inject = ['$uibModalInstance', 'entity', 'TraderCA'];

    function TraderCADeleteController($uibModalInstance, entity, TraderCA) {
        var vm = this;

        vm.traderCA = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraderCA.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
