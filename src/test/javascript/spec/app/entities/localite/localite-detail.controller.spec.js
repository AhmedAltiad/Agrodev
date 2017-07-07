'use strict';

describe('Controller Tests', function() {

    describe('Localite Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLocalite, MockDelegation, MockEmployer, MockAnnonce, MockProduit, MockEmpActualite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLocalite = jasmine.createSpy('MockLocalite');
            MockDelegation = jasmine.createSpy('MockDelegation');
            MockEmployer = jasmine.createSpy('MockEmployer');
            MockAnnonce = jasmine.createSpy('MockAnnonce');
            MockProduit = jasmine.createSpy('MockProduit');
            MockEmpActualite = jasmine.createSpy('MockEmpActualite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Localite': MockLocalite,
                'Delegation': MockDelegation,
                'Employer': MockEmployer,
                'Annonce': MockAnnonce,
                'Produit': MockProduit,
                'EmpActualite': MockEmpActualite
            };
            createController = function() {
                $injector.get('$controller')("LocaliteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:localiteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
