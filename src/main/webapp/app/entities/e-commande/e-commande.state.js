(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('e-commande', {
            parent: 'entity',
            url: '/e-commande',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.eCommande.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/e-commande/e-commandes.html',
                    controller: 'ECommandeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eCommande');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('e-commande-detail', {
            parent: 'e-commande',
            url: '/e-commande/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.eCommande.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/e-commande/e-commande-detail.html',
                    controller: 'ECommandeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eCommande');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ECommande', function($stateParams, ECommande) {
                    return ECommande.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'e-commande',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('e-commande-detail.edit', {
            parent: 'e-commande-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande/e-commande-dialog.html',
                    controller: 'ECommandeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ECommande', function(ECommande) {
                            return ECommande.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('e-commande.new', {
            parent: 'e-commande',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande/e-commande-dialog.html',
                    controller: 'ECommandeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                etat: null,
                                prix: null,
                                quantite: null,
                                date: null,
                                numcommande: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('e-commande', null, { reload: 'e-commande' });
                }, function() {
                    $state.go('e-commande');
                });
            }]
        })
        .state('e-commande.edit', {
            parent: 'e-commande',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande/e-commande-dialog.html',
                    controller: 'ECommandeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ECommande', function(ECommande) {
                            return ECommande.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('e-commande', null, { reload: 'e-commande' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('e-commande.delete', {
            parent: 'e-commande',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande/e-commande-delete-dialog.html',
                    controller: 'ECommandeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ECommande', function(ECommande) {
                            return ECommande.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('e-commande', null, { reload: 'e-commande' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
