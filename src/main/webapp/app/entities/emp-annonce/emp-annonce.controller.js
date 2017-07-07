(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpAnnonceController', EmpAnnonceController);

    EmpAnnonceController.$inject = ['EmpAnnonce', 'EmpAnnonceSearch'];

    function EmpAnnonceController(EmpAnnonce, EmpAnnonceSearch) {

        var vm = this;

        vm.empAnnonces = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            EmpAnnonce.query(function(result) {
                vm.empAnnonces = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmpAnnonceSearch.query({query: vm.searchQuery}, function(result) {
                vm.empAnnonces = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
