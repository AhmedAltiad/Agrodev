(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PalierController', PalierController);

    PalierController.$inject = ['Palier', 'PalierSearch'];

    function PalierController(Palier, PalierSearch) {

        var vm = this;

        vm.paliers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Palier.query(function(result) {
                vm.paliers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PalierSearch.query({query: vm.searchQuery}, function(result) {
                vm.paliers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
