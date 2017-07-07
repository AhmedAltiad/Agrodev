(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('activite-agricole', {
            parent: 'entity',
            url: '/activite-agricole',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.activiteAgricole.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/activite-agricole/activite-agricoles.html',
                    controller: 'ActiviteAgricoleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('activiteAgricole');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('activite-agricole-detail', {
            parent: 'activite-agricole',
            url: '/activite-agricole/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.activiteAgricole.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/activite-agricole/activite-agricole-detail.html',
                    controller: 'ActiviteAgricoleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('activiteAgricole');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ActiviteAgricole', function($stateParams, ActiviteAgricole) {
                    return ActiviteAgricole.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'activite-agricole',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('activite-agricole-detail.edit', {
            parent: 'activite-agricole-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activite-agricole/activite-agricole-dialog.html',
                    controller: 'ActiviteAgricoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ActiviteAgricole', function(ActiviteAgricole) {
                            return ActiviteAgricole.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('activite-agricole.new', {
            parent: 'activite-agricole',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activite-agricole/activite-agricole-dialog.html',
                    controller: 'ActiviteAgricoleDialogController',
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
                    $state.go('activite-agricole', null, { reload: 'activite-agricole' });
                }, function() {
                    $state.go('activite-agricole');
                });
            }]
        })
        .state('activite-agricole.edit', {
            parent: 'activite-agricole',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activite-agricole/activite-agricole-dialog.html',
                    controller: 'ActiviteAgricoleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ActiviteAgricole', function(ActiviteAgricole) {
                            return ActiviteAgricole.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('activite-agricole', null, { reload: 'activite-agricole' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('activite-agricole.delete', {
            parent: 'activite-agricole',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/activite-agricole/activite-agricole-delete-dialog.html',
                    controller: 'ActiviteAgricoleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ActiviteAgricole', function(ActiviteAgricole) {
                            return ActiviteAgricole.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('activite-agricole', null, { reload: 'activite-agricole' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
