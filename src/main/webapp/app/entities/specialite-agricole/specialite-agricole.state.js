(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('specialite-agricole', {
            parent: 'entity',
            url: '/specialite-agricole',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.specialiteAgricole.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/specialite-agricole/specialite-agricoles.html',
                    controller: 'SpecialiteAgricoleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('specialiteAgricole');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('specialite-agricole-detail', {
            parent: 'specialite-agricole',
            url: '/specialite-agricole/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.specialiteAgricole.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/specialite-agricole/specialite-agricole-detail.html',
                    controller: 'SpecialiteAgricoleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('specialiteAgricole');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'SpecialiteAgricole', function($stateParams, SpecialiteAgricole) {
                    return SpecialiteAgricole.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'specialite-agricole',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('specialite-agricole-detail.edit', {
            parent: 'specialite-agricole-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/specialite-agricole/specialite-agricole-dialog.html',
                    controller: 'SpecialiteAgricoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SpecialiteAgricole', function(SpecialiteAgricole) {
                            return SpecialiteAgricole.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('specialite-agricole.new', {
            parent: 'specialite-agricole',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/specialite-agricole/specialite-agricole-dialog.html',
                    controller: 'SpecialiteAgricoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('specialite-agricole', null, { reload: 'specialite-agricole' });
                }, function() {
                    $state.go('specialite-agricole');
                });
            }]
        })
        .state('specialite-agricole.edit', {
            parent: 'specialite-agricole',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/specialite-agricole/specialite-agricole-dialog.html',
                    controller: 'SpecialiteAgricoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['SpecialiteAgricole', function(SpecialiteAgricole) {
                            return SpecialiteAgricole.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('specialite-agricole', null, { reload: 'specialite-agricole' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('specialite-agricole.delete', {
            parent: 'specialite-agricole',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/specialite-agricole/specialite-agricole-delete-dialog.html',
                    controller: 'SpecialiteAgricoleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['SpecialiteAgricole', function(SpecialiteAgricole) {
                            return SpecialiteAgricole.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('specialite-agricole', null, { reload: 'specialite-agricole' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
