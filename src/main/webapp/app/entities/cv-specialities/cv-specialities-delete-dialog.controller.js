(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CvSpecialitiesDeleteController',CvSpecialitiesDeleteController);

    CvSpecialitiesDeleteController.$inject = ['$uibModalInstance', 'entity', 'CvSpecialities'];

    function CvSpecialitiesDeleteController($uibModalInstance, entity, CvSpecialities) {
        var vm = this;

        vm.cvSpecialities = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CvSpecialities.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
