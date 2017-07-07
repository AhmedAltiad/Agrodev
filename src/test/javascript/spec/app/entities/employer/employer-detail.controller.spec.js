'use strict';

describe('Controller Tests', function() {

    describe('Employer Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmployer, MockProfil, MockLocalite, MockEmpAnnonce;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmployer = jasmine.createSpy('MockEmployer');
            MockProfil = jasmine.createSpy('MockProfil');
            MockLocalite = jasmine.createSpy('MockLocalite');
            MockEmpAnnonce = jasmine.createSpy('MockEmpAnnonce');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Employer': MockEmployer,
                'Profil': MockProfil,
                'Localite': MockLocalite,
                'EmpAnnonce': MockEmpAnnonce
            };
            createController = function() {
                $injector.get('$controller')("EmployerDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:employerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
