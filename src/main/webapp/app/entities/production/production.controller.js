(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProductionController', ProductionController);

    ProductionController.$inject = ['Production', 'ProductionSearch'];

    function ProductionController(Production, ProductionSearch) {

        var vm = this;

        vm.productions = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Production.query(function(result) {
                vm.productions = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProductionSearch.query({query: vm.searchQuery}, function(result) {
                vm.productions = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
