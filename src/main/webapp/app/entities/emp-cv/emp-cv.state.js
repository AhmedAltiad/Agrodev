(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('emp-cv', {
            parent: 'entity',
            url: '/emp-cv',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.empCV.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp-cv/emp-cvs.html',
                    controller: 'EmpCVController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empCV');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('emp-cv-detail', {
            parent: 'emp-cv',
            url: '/emp-cv/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.empCV.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/emp-cv/emp-cv-detail.html',
                    controller: 'EmpCVDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('empCV');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EmpCV', function($stateParams, EmpCV) {
                    return EmpCV.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'emp-cv',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('emp-cv-detail.edit', {
            parent: 'emp-cv-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-cv/emp-cv-dialog.html',
                    controller: 'EmpCVDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmpCV', function(EmpCV) {
                            return EmpCV.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp-cv.new', {
            parent: 'emp-cv',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-cv/emp-cv-dialog.html',
                    controller: 'EmpCVDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                title: null,
                                anneexperience: null,
                                permis: null,
                                niveauScolaire: null,
                                diplome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('emp-cv', null, { reload: 'emp-cv' });
                }, function() {
                    $state.go('emp-cv');
                });
            }]
        })
        .state('emp-cv.edit', {
            parent: 'emp-cv',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-cv/emp-cv-dialog.html',
                    controller: 'EmpCVDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EmpCV', function(EmpCV) {
                            return EmpCV.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp-cv', null, { reload: 'emp-cv' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('emp-cv.delete', {
            parent: 'emp-cv',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/emp-cv/emp-cv-delete-dialog.html',
                    controller: 'EmpCVDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EmpCV', function(EmpCV) {
                            return EmpCV.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('emp-cv', null, { reload: 'emp-cv' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
