(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceHistoriqueController', AnnonceHistoriqueController);

    AnnonceHistoriqueController.$inject = ['AnnonceHistorique', 'AnnonceHistoriqueSearch'];

    function AnnonceHistoriqueController(AnnonceHistorique, AnnonceHistoriqueSearch) {

        var vm = this;

        vm.annonceHistoriques = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            AnnonceHistorique.query(function(result) {
                vm.annonceHistoriques = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AnnonceHistoriqueSearch.query({query: vm.searchQuery}, function(result) {
                vm.annonceHistoriques = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
