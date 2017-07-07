(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('TraderCADialogController', TraderCADialogController);

    TraderCADialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'TraderCA', 'Profil', 'Desk', 'Trade'];

    function TraderCADialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, TraderCA, Profil, Desk, Trade) {
        var vm = this;

        vm.traderCA = entity;
        vm.clear = clear;
        vm.save = save;
        vm.profils = Profil.query({filter: 'traderca-is-null'});
        $q.all([vm.traderCA.$promise, vm.profils.$promise]).then(function() {
            if (!vm.traderCA.profil || !vm.traderCA.profil.id) {
                return $q.reject();
            }
            return Profil.get({id : vm.traderCA.profil.id}).$promise;
        }).then(function(profil) {
            vm.profils.push(profil);
        });
        vm.desks = Desk.query();
        vm.trades = Trade.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.traderCA.id !== null) {
                TraderCA.update(vm.traderCA, onSaveSuccess, onSaveError);
            } else {
                TraderCA.save(vm.traderCA, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('agroBourse360SiApp:traderCAUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
