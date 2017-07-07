'use strict';

describe('Controller Tests', function() {

    describe('EmpCV Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmpCV, MockFormation, MockEmployee, MockCvSpecialities;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmpCV = jasmine.createSpy('MockEmpCV');
            MockFormation = jasmine.createSpy('MockFormation');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockCvSpecialities = jasmine.createSpy('MockCvSpecialities');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EmpCV': MockEmpCV,
                'Formation': MockFormation,
                'Employee': MockEmployee,
                'CvSpecialities': MockCvSpecialities
            };
            createController = function() {
                $injector.get('$controller')("EmpCVDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:empCVUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
