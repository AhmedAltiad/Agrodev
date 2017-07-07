(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmpCVController', EmpCVController);

    EmpCVController.$inject = ['EmpCV', 'EmpCVSearch'];

    function EmpCVController(EmpCV, EmpCVSearch) {

        var vm = this;

        vm.empCVS = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            EmpCV.query(function(result) {
                vm.empCVS = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmpCVSearch.query({query: vm.searchQuery}, function(result) {
                vm.empCVS = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
