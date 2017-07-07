(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .controller('EmployerController', EmployerController);

    EmployerController.$inject = ['Employer', 'EmployerSearch'];

    function EmployerController(Employer, EmployerSearch) {

        var vm = this;

        vm.employers = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Employer.query(function(result) {
                vm.employers = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            EmployerSearch.query({query: vm.searchQuery}, function(result) {
                vm.employers = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
