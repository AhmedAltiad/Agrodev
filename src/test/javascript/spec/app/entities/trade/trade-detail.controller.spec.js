'use strict';

describe('Controller Tests', function() {

    describe('Trade Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTrade, MockCurrency, MockVariete, MockTraderAGB, MockTraderCA;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTrade = jasmine.createSpy('MockTrade');
            MockCurrency = jasmine.createSpy('MockCurrency');
            MockVariete = jasmine.createSpy('MockVariete');
            MockTraderAGB = jasmine.createSpy('MockTraderAGB');
            MockTraderCA = jasmine.createSpy('MockTraderCA');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Trade': MockTrade,
                'Currency': MockCurrency,
                'Variete': MockVariete,
                'TraderAGB': MockTraderAGB,
                'TraderCA': MockTraderCA
            };
            createController = function() {
                $injector.get('$controller')("TradeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:tradeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
