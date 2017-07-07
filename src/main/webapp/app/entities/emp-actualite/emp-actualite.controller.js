(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpActualiteController', EmpActualiteController);

    EmpActualiteController.$inject = ['EmpActualite', 'EmpActualiteSearch'];

    function EmpActualiteController(EmpActualite, EmpActualiteSearch) {

        var vm = this;

        vm.empActualites = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            EmpActualite.query(function(result) {
                vm.empActualites = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmpActualiteSearch.query({query: vm.searchQuery}, function(result) {
                vm.empActualites = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
