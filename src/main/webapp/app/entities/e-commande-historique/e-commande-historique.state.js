(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('e-commande-historique', {
            parent: 'entity',
            url: '/e-commande-historique',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.eCommandeHistorique.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/e-commande-historique/e-commande-historiques.html',
                    controller: 'ECommandeHistoriqueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eCommandeHistorique');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('e-commande-historique-detail', {
            parent: 'e-commande-historique',
            url: '/e-commande-historique/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.eCommandeHistorique.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/e-commande-historique/e-commande-historique-detail.html',
                    controller: 'ECommandeHistoriqueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('eCommandeHistorique');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ECommandeHistorique', function($stateParams, ECommandeHistorique) {
                    return ECommandeHistorique.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'e-commande-historique',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('e-commande-historique-detail.edit', {
            parent: 'e-commande-historique-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande-historique/e-commande-historique-dialog.html',
                    controller: 'ECommandeHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ECommandeHistorique', function(ECommandeHistorique) {
                            return ECommandeHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('e-commande-historique.new', {
            parent: 'e-commande-historique',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande-historique/e-commande-historique-dialog.html',
                    controller: 'ECommandeHistoriqueDialogController',
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
                    $state.go('e-commande-historique', null, { reload: 'e-commande-historique' });
                }, function() {
                    $state.go('e-commande-historique');
                });
            }]
        })
        .state('e-commande-historique.edit', {
            parent: 'e-commande-historique',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande-historique/e-commande-historique-dialog.html',
                    controller: 'ECommandeHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ECommandeHistorique', function(ECommandeHistorique) {
                            return ECommandeHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('e-commande-historique', null, { reload: 'e-commande-historique' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('e-commande-historique.delete', {
            parent: 'e-commande-historique',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/e-commande-historique/e-commande-historique-delete-dialog.html',
                    controller: 'ECommandeHistoriqueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ECommandeHistorique', function(ECommandeHistorique) {
                            return ECommandeHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('e-commande-historique', null, { reload: 'e-commande-historique' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
