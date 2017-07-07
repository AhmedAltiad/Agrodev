(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CategorieController', CategorieController);

    CategorieController.$inject = ['Categorie', 'CategorieSearch'];

    function CategorieController(Categorie, CategorieSearch) {

        var vm = this;

        vm.categories = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Categorie.query(function(result) {
                vm.categories = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CategorieSearch.query({query: vm.searchQuery}, function(result) {
                vm.categories = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
