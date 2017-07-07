(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('variete', {
            parent: 'entity',
            url: '/variete',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.variete.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variete/varietes.html',
                    controller: 'VarieteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('variete');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('variete-detail', {
            parent: 'variete',
            url: '/variete/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.variete.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/variete/variete-detail.html',
                    controller: 'VarieteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('variete');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Variete', function($stateParams, Variete) {
                    return Variete.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'variete',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('variete-detail.edit', {
            parent: 'variete-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variete/variete-dialog.html',
                    controller: 'VarieteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Variete', function(Variete) {
                            return Variete.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variete.new', {
            parent: 'variete',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variete/variete-dialog.html',
                    controller: 'VarieteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                image: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('variete', null, { reload: 'variete' });
                }, function() {
                    $state.go('variete');
                });
            }]
        })
        .state('variete.edit', {
            parent: 'variete',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variete/variete-dialog.html',
                    controller: 'VarieteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Variete', function(Variete) {
                            return Variete.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variete', null, { reload: 'variete' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('variete.delete', {
            parent: 'variete',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/variete/variete-delete-dialog.html',
                    controller: 'VarieteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Variete', function(Variete) {
                            return Variete.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('variete', null, { reload: 'variete' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
