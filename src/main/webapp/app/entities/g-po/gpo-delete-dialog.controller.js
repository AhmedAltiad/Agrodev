(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('GPODeleteController',GPODeleteController);

    GPODeleteController.$inject = ['$uibModalInstance', 'entity', 'GPO'];

    function GPODeleteController($uibModalInstance, entity, GPO) {
        var vm = this;

        vm.gPO = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GPO.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
