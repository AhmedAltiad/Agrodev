'use strict';

describe('Controller Tests', function() {

    describe('TraderCA Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTraderCA, MockProfil, MockDesk, MockTrade;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTraderCA = jasmine.createSpy('MockTraderCA');
            MockProfil = jasmine.createSpy('MockProfil');
            MockDesk = jasmine.createSpy('MockDesk');
            MockTrade = jasmine.createSpy('MockTrade');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TraderCA': MockTraderCA,
                'Profil': MockProfil,
                'Desk': MockDesk,
                'Trade': MockTrade
            };
            createController = function() {
                $injector.get('$controller')("TraderCADetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:traderCAUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
