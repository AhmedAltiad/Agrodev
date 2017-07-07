(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('agriculteur', {
            parent: 'entity',
            url: '/agriculteur',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.agriculteur.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agriculteur/agriculteurs.html',
                    controller: 'AgriculteurController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agriculteur');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('agriculteur-detail', {
            parent: 'agriculteur',
            url: '/agriculteur/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.agriculteur.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/agriculteur/agriculteur-detail.html',
                    controller: 'AgriculteurDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('agriculteur');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Agriculteur', function($stateParams, Agriculteur) {
                    return Agriculteur.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'agriculteur',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('agriculteur-detail.edit', {
            parent: 'agriculteur-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agriculteur/agriculteur-dialog.html',
                    controller: 'AgriculteurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agriculteur', function(Agriculteur) {
                            return Agriculteur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agriculteur.new', {
            parent: 'agriculteur',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agriculteur/agriculteur-dialog.html',
                    controller: 'AgriculteurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                typeSociete: null,
                                raisonSociale: null,
                                firstName: null,
                                lastName: null,
                                adresse: null,
                                adresseLivraison: null,
                                telephone: null,
                                typeAgricole: null,
                                superficie: null,
                                superficieIrriguee: null,
                                bio: null,
                                nbreEmployeePermanant: null,
                                specialiteProduction: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('agriculteur', null, { reload: 'agriculteur' });
                }, function() {
                    $state.go('agriculteur');
                });
            }]
        })
        .state('agriculteur.edit', {
            parent: 'agriculteur',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agriculteur/agriculteur-dialog.html',
                    controller: 'AgriculteurDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Agriculteur', function(Agriculteur) {
                            return Agriculteur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agriculteur', null, { reload: 'agriculteur' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('agriculteur.delete', {
            parent: 'agriculteur',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/agriculteur/agriculteur-delete-dialog.html',
                    controller: 'AgriculteurDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Agriculteur', function(Agriculteur) {
                            return Agriculteur.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('agriculteur', null, { reload: 'agriculteur' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
