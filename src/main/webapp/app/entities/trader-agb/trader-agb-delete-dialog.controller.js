(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderAGBDeleteController',TraderAGBDeleteController);

    TraderAGBDeleteController.$inject = ['$uibModalInstance', 'entity', 'TraderAGB'];

    function TraderAGBDeleteController($uibModalInstance, entity, TraderAGB) {
        var vm = this;

        vm.traderAGB = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TraderAGB.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
