(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('AnnonceChangementController', AnnonceChangementController);

    AnnonceChangementController.$inject = ['AnnonceChangement', 'AnnonceChangementSearch'];

    function AnnonceChangementController(AnnonceChangement, AnnonceChangementSearch) {

        var vm = this;

        vm.annonceChangements = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            AnnonceChangement.query(function(result) {
                vm.annonceChangements = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AnnonceChangementSearch.query({query: vm.searchQuery}, function(result) {
                vm.annonceChangements = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
