(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('employer', {
            parent: 'entity',
            url: '/employer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.employer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employer/employers.html',
                    controller: 'EmployerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('employer-detail', {
            parent: 'employer',
            url: '/employer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.employer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/employer/employer-detail.html',
                    controller: 'EmployerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('employer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Employer', function($stateParams, Employer) {
                    return Employer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'employer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('employer-detail.edit', {
            parent: 'employer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employer/employer-dialog.html',
                    controller: 'EmployerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employer', function(Employer) {
                            return Employer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employer.new', {
            parent: 'employer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employer/employer-dialog.html',
                    controller: 'EmployerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                companyName: null,
                                type: null,
                                companyDescription: null,
                                adresseSiege: null,
                                telephone: null,
                                nbreEmployeePermanant: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('employer', null, { reload: 'employer' });
                }, function() {
                    $state.go('employer');
                });
            }]
        })
        .state('employer.edit', {
            parent: 'employer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employer/employer-dialog.html',
                    controller: 'EmployerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Employer', function(Employer) {
                            return Employer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employer', null, { reload: 'employer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('employer.delete', {
            parent: 'employer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/employer/employer-delete-dialog.html',
                    controller: 'EmployerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Employer', function(Employer) {
                            return Employer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('employer', null, { reload: 'employer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
