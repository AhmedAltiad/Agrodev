(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('annonce-historique', {
            parent: 'entity',
            url: '/annonce-historique',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.annonceHistorique.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/annonce-historique/annonce-historiques.html',
                    controller: 'AnnonceHistoriqueController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('annonceHistorique');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('annonce-historique-detail', {
            parent: 'annonce-historique',
            url: '/annonce-historique/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.annonceHistorique.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/annonce-historique/annonce-historique-detail.html',
                    controller: 'AnnonceHistoriqueDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('annonceHistorique');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'AnnonceHistorique', function($stateParams, AnnonceHistorique) {
                    return AnnonceHistorique.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'annonce-historique',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('annonce-historique-detail.edit', {
            parent: 'annonce-historique-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-historique/annonce-historique-dialog.html',
                    controller: 'AnnonceHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AnnonceHistorique', function(AnnonceHistorique) {
                            return AnnonceHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('annonce-historique.new', {
            parent: 'annonce-historique',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-historique/annonce-historique-dialog.html',
                    controller: 'AnnonceHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numAnnonce: null,
                                etat: null,
                                createddate: null,
                                lastmodifieddate: null,
                                dateactivation: null,
                                dateexpiration: null,
                                prix: null,
                                quantite: null,
                                image: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('annonce-historique', null, { reload: 'annonce-historique' });
                }, function() {
                    $state.go('annonce-historique');
                });
            }]
        })
        .state('annonce-historique.edit', {
            parent: 'annonce-historique',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-historique/annonce-historique-dialog.html',
                    controller: 'AnnonceHistoriqueDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AnnonceHistorique', function(AnnonceHistorique) {
                            return AnnonceHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('annonce-historique', null, { reload: 'annonce-historique' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('annonce-historique.delete', {
            parent: 'annonce-historique',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/annonce-historique/annonce-historique-delete-dialog.html',
                    controller: 'AnnonceHistoriqueDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AnnonceHistorique', function(AnnonceHistorique) {
                            return AnnonceHistorique.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('annonce-historique', null, { reload: 'annonce-historique' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
