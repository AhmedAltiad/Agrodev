(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('gpo', {
            parent: 'entity',
            url: '/gpo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.gPO.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/g-po/g-pos.html',
                    controller: 'GPOController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gPO');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('gpo-detail', {
            parent: 'gpo',
            url: '/gpo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.gPO.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/g-po/gpo-detail.html',
                    controller: 'GPODetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('gPO');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GPO', function($stateParams, GPO) {
                    return GPO.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'gpo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('gpo-detail.edit', {
            parent: 'gpo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/g-po/gpo-dialog.html',
                    controller: 'GPODialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GPO', function(GPO) {
                            return GPO.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gpo.new', {
            parent: 'gpo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/g-po/gpo-dialog.html',
                    controller: 'GPODialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                societepostale: null,
                                societelegale: null,
                                qualite: null,
                                prenom: null,
                                nom: null,
                                addadresse1: null,
                                addadresse2: null,
                                boitepostale: null,
                                addcodepostal: null,
                                addville: null,
                                telephoneunique: null,
                                telephone: null,
                                telephoneNumInternational: null,
                                messagerie: null,
                                messagerieunique: null,
                                region: null,
                                departement: null,
                                numdepartement: null,
                                categorie: null,
                                codeNafImport: null,
                                sivet: null,
                                web: null,
                                populationtotale: null,
                                reseau: null,
                                enseigne: null,
                                statutEtablissement: null,
                                statutJudiciaire: null,
                                ca: null,
                                capitalSocial: null,
                                dateCreationEntreprise: null,
                                effectifs: null,
                                fax: null,
                                faxfaxinginternational: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('gpo', null, { reload: 'gpo' });
                }, function() {
                    $state.go('gpo');
                });
            }]
        })
        .state('gpo.edit', {
            parent: 'gpo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/g-po/gpo-dialog.html',
                    controller: 'GPODialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GPO', function(GPO) {
                            return GPO.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gpo', null, { reload: 'gpo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('gpo.delete', {
            parent: 'gpo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/g-po/gpo-delete-dialog.html',
                    controller: 'GPODeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GPO', function(GPO) {
                            return GPO.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('gpo', null, { reload: 'gpo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
