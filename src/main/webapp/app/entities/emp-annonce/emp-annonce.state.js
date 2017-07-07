(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('emp-annonce', {
            parent: 'entity',
            url: '/emp-annonce',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.empAnnonce.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp-annonce/emp-annonces.html',
                    controller: 'EmpAnnonceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empAnnonce');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('emp-annonce-detail', {
            parent: 'emp-annonce',
            url: '/emp-annonce/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.empAnnonce.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp-annonce/emp-annonce-detail.html',
                    controller: 'EmpAnnonceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empAnnonce');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmpAnnonce', function($stateParams, EmpAnnonce) {
                    return EmpAnnonce.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'emp-annonce',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('emp-annonce-detail.edit', {
            parent: 'emp-annonce-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-annonce/emp-annonce-dialog.html',
                    controller: 'EmpAnnonceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmpAnnonce', function(EmpAnnonce) {
                            return EmpAnnonce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp-annonce.new', {
            parent: 'emp-annonce',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-annonce/emp-annonce-dialog.html',
                    controller: 'EmpAnnonceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                initDate: null,
                                finDate: null,
                                title: null,
                                typeContrat: null,
                                shortDescription: null,
                                longDescription: null,
                                validation: null,
                                adresse: null,
                                nbredepostes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('emp-annonce', null, { reload: 'emp-annonce' });
                }, function() {
                    $state.go('emp-annonce');
                });
            }]
        })
        .state('emp-annonce.edit', {
            parent: 'emp-annonce',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-annonce/emp-annonce-dialog.html',
                    controller: 'EmpAnnonceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmpAnnonce', function(EmpAnnonce) {
                            return EmpAnnonce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp-annonce', null, { reload: 'emp-annonce' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp-annonce.delete', {
            parent: 'emp-annonce',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-annonce/emp-annonce-delete-dialog.html',
                    controller: 'EmpAnnonceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmpAnnonce', function(EmpAnnonce) {
                            return EmpAnnonce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp-annonce', null, { reload: 'emp-annonce' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
