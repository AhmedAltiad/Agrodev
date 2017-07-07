(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ProduitController', ProduitController);

    ProduitController.$inject = ['Produit', 'ProduitSearch'];

    function ProduitController(Produit, ProduitSearch) {

        var vm = this;

        vm.produits = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Produit.query(function(result) {
                vm.produits = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProduitSearch.query({query: vm.searchQuery}, function(result) {
                vm.produits = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
