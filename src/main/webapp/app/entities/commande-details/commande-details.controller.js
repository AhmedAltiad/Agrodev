(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('CommandeDetailsController', CommandeDetailsController);

    CommandeDetailsController.$inject = ['CommandeDetails', 'CommandeDetailsSearch'];

    function CommandeDetailsController(CommandeDetails, CommandeDetailsSearch) {

        var vm = this;

        vm.commandeDetails = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            CommandeDetails.query(function(result) {
                vm.commandeDetails = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            CommandeDetailsSearch.query({query: vm.searchQuery}, function(result) {
                vm.commandeDetails = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
