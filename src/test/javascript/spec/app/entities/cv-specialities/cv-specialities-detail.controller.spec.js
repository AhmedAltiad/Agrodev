'use strict';

describe('Controller Tests', function() {

    describe('CvSpecialities Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCvSpecialities, MockEmpCV;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCvSpecialities = jasmine.createSpy('MockCvSpecialities');
            MockEmpCV = jasmine.createSpy('MockEmpCV');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'CvSpecialities': MockCvSpecialities,
                'EmpCV': MockEmpCV
            };
            createController = function() {
                $injector.get('$controller')("CvSpecialitiesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:cvSpecialitiesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
