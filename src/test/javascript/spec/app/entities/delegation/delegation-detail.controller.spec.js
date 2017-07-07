'use strict';

describe('Controller Tests', function() {

    describe('Delegation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDelegation, MockGouvernorat, MockLocalite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDelegation = jasmine.createSpy('MockDelegation');
            MockGouvernorat = jasmine.createSpy('MockGouvernorat');
            MockLocalite = jasmine.createSpy('MockLocalite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Delegation': MockDelegation,
                'Gouvernorat': MockGouvernorat,
                'Localite': MockLocalite
            };
            createController = function() {
                $injector.get('$controller')("DelegationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:delegationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
