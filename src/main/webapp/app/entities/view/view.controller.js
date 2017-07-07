(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('ViewController', ViewController);

    ViewController.$inject = ['View', 'ViewSearch'];

    function ViewController(View, ViewSearch) {

        var vm = this;

        vm.views = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            View.query(function(result) {
                vm.views = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ViewSearch.query({query: vm.searchQuery}, function(result) {
                vm.views = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
