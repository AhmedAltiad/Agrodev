(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('PaysController', PaysController);

    PaysController.$inject = ['Pays', 'PaysSearch'];

    function PaysController(Pays, PaysSearch) {

        var vm = this;

        vm.pays = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Pays.query(function(result) {
                vm.pays = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PaysSearch.query({query: vm.searchQuery}, function(result) {
                vm.pays = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
