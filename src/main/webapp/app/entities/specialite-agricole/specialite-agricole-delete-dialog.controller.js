(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('SpecialiteAgricoleDeleteController',SpecialiteAgricoleDeleteController);

    SpecialiteAgricoleDeleteController.$inject = ['$uibModalInstance', 'entity', 'SpecialiteAgricole'];

    function SpecialiteAgricoleDeleteController($uibModalInstance, entity, SpecialiteAgricole) {
        var vm = this;

        vm.specialiteAgricole = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SpecialiteAgricole.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
