(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpAnnonceDeleteController',EmpAnnonceDeleteController);

    EmpAnnonceDeleteController.$inject = ['$uibModalInstance', 'entity', 'EmpAnnonce'];

    function EmpAnnonceDeleteController($uibModalInstance, entity, EmpAnnonce) {
        var vm = this;

        vm.empAnnonce = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EmpAnnonce.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
