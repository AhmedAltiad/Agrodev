(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('delegation', {
            parent: 'entity',
            url: '/delegation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.delegation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/delegation/delegations.html',
                    controller: 'DelegationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('delegation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('delegation-detail', {
            parent: 'delegation',
            url: '/delegation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.delegation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/delegation/delegation-detail.html',
                    controller: 'DelegationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('delegation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Delegation', function($stateParams, Delegation) {
                    return Delegation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'delegation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('delegation-detail.edit', {
            parent: 'delegation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delegation/delegation-dialog.html',
                    controller: 'DelegationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Delegation', function(Delegation) {
                            return Delegation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('delegation.new', {
            parent: 'delegation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delegation/delegation-dialog.html',
                    controller: 'DelegationDialogController',
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
                    $state.go('delegation', null, { reload: 'delegation' });
                }, function() {
                    $state.go('delegation');
                });
            }]
        })
        .state('delegation.edit', {
            parent: 'delegation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delegation/delegation-dialog.html',
                    controller: 'DelegationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Delegation', function(Delegation) {
                            return Delegation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('delegation', null, { reload: 'delegation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('delegation.delete', {
            parent: 'delegation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/delegation/delegation-delete-dialog.html',
                    controller: 'DelegationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Delegation', function(Delegation) {
                            return Delegation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('delegation', null, { reload: 'delegation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
