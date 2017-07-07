(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeController', ECommandeController);

    ECommandeController.$inject = ['ECommande', 'ECommandeSearch'];

    function ECommandeController(ECommande, ECommandeSearch) {

        var vm = this;

        vm.eCommandes = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ECommande.query(function(result) {
                vm.eCommandes = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ECommandeSearch.query({query: vm.searchQuery}, function(result) {
                vm.eCommandes = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
