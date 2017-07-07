(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ECommandeHistoriqueController', ECommandeHistoriqueController);

    ECommandeHistoriqueController.$inject = ['ECommandeHistorique', 'ECommandeHistoriqueSearch'];

    function ECommandeHistoriqueController(ECommandeHistorique, ECommandeHistoriqueSearch) {

        var vm = this;

        vm.eCommandeHistoriques = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ECommandeHistorique.query(function(result) {
                vm.eCommandeHistoriques = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ECommandeHistoriqueSearch.query({query: vm.searchQuery}, function(result) {
                vm.eCommandeHistoriques = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
