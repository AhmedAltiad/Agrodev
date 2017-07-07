(function() {
    'use strict';

    angular
        .module('agroBourse360SiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cv-specialities', {
            parent: 'entity',
            url: '/cv-specialities',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.cvSpecialities.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cv-specialities/cv-specialities.html',
                    controller: 'CvSpecialitiesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cvSpecialities');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cv-specialities-detail', {
            parent: 'cv-specialities',
            url: '/cv-specialities/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'agroBourse360SiApp.cvSpecialities.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cv-specialities/cv-specialities-detail.html',
                    controller: 'CvSpecialitiesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cvSpecialities');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CvSpecialities', function($stateParams, CvSpecialities) {
                    return CvSpecialities.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'cv-specialities',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('cv-specialities-detail.edit', {
            parent: 'cv-specialities-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cv-specialities/cv-specialities-dialog.html',
                    controller: 'CvSpecialitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CvSpecialities', function(CvSpecialities) {
                            return CvSpecialities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cv-specialities.new', {
            parent: 'cv-specialities',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cv-specialities/cv-specialities-dialog.html',
                    controller: 'CvSpecialitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('cv-specialities', null, { reload: 'cv-specialities' });
                }, function() {
                    $state.go('cv-specialities');
                });
            }]
        })
        .state('cv-specialities.edit', {
            parent: 'cv-specialities',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cv-specialities/cv-specialities-dialog.html',
                    controller: 'CvSpecialitiesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CvSpecialities', function(CvSpecialities) {
                            return CvSpecialities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cv-specialities', null, { reload: 'cv-specialities' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cv-specialities.delete', {
            parent: 'cv-specialities',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cv-specialities/cv-specialities-delete-dialog.html',
                    controller: 'CvSpecialitiesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CvSpecialities', function(CvSpecialities) {
                            return CvSpecialities.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cv-specialities', null, { reload: 'cv-specialities' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
