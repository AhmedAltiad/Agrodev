(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AgriculteurController', AgriculteurController);

    AgriculteurController.$inject = ['Agriculteur', 'AgriculteurSearch'];

    function AgriculteurController(Agriculteur, AgriculteurSearch) {

        var vm = this;

        vm.agriculteurs = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Agriculteur.query(function(result) {
                vm.agriculteurs = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AgriculteurSearch.query({query: vm.searchQuery}, function(result) {
                vm.agriculteurs = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
