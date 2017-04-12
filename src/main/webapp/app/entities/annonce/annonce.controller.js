(function() {
    'use strict';

    angular
        .module('agroBourseApp')
        .controller('AnnonceController', AnnonceController);

    AnnonceController.$inject = ['Annonce', 'AnnonceSearch'];

    function AnnonceController(Annonce, AnnonceSearch) {

        var vm = this;

        vm.annonces = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Annonce.query(function(result) {
                vm.annonces = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AnnonceSearch.query({query: vm.searchQuery}, function(result) {
                vm.annonces = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
