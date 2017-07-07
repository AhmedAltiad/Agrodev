(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeController', CommandeController);

    CommandeController.$inject = ['Commande', 'CommandeSearch'];

    function CommandeController(Commande, CommandeSearch) {

        var vm = this;

        vm.commandes = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Commande.query(function(result) {
                vm.commandes = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CommandeSearch.query({query: vm.searchQuery}, function(result) {
                vm.commandes = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
