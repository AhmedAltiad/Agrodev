(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('portefolio', {
            parent: 'entity',
            url: '/portefolio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.portefolio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/portefolio/portefolios.html',
                    controller: 'PortefolioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('portefolio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('portefolio-detail', {
            parent: 'portefolio',
            url: '/portefolio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.portefolio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/portefolio/portefolio-detail.html',
                    controller: 'PortefolioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('portefolio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Portefolio', function($stateParams, Portefolio) {
                    return Portefolio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'portefolio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('portefolio-detail.edit', {
            parent: 'portefolio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portefolio/portefolio-dialog.html',
                    controller: 'PortefolioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Portefolio', function(Portefolio) {
                            return Portefolio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('portefolio.new', {
            parent: 'portefolio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portefolio/portefolio-dialog.html',
                    controller: 'PortefolioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nbrerealisation: null,
                                nbreencours: null,
                                specialite: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('portefolio', null, { reload: 'portefolio' });
                }, function() {
                    $state.go('portefolio');
                });
            }]
        })
        .state('portefolio.edit', {
            parent: 'portefolio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portefolio/portefolio-dialog.html',
                    controller: 'PortefolioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Portefolio', function(Portefolio) {
                            return Portefolio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('portefolio', null, { reload: 'portefolio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('portefolio.delete', {
            parent: 'portefolio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/portefolio/portefolio-delete-dialog.html',
                    controller: 'PortefolioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Portefolio', function(Portefolio) {
                            return Portefolio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('portefolio', null, { reload: 'portefolio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
