'use strict';

describe('Controller Tests', function() {

    describe('Gouvernorat Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGouvernorat, MockRegion, MockDelegation, MockDesk;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGouvernorat = jasmine.createSpy('MockGouvernorat');
            MockRegion = jasmine.createSpy('MockRegion');
            MockDelegation = jasmine.createSpy('MockDelegation');
            MockDesk = jasmine.createSpy('MockDesk');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Gouvernorat': MockGouvernorat,
                'Region': MockRegion,
                'Delegation': MockDelegation,
                'Desk': MockDesk
            };
            createController = function() {
                $injector.get('$controller')("GouvernoratDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:gouvernoratUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
