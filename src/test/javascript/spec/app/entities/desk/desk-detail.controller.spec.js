'use strict';

describe('Controller Tests', function() {

    describe('Desk Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDesk, MockTraderCA, MockTraderAGB, MockGouvernorat;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDesk = jasmine.createSpy('MockDesk');
            MockTraderCA = jasmine.createSpy('MockTraderCA');
            MockTraderAGB = jasmine.createSpy('MockTraderAGB');
            MockGouvernorat = jasmine.createSpy('MockGouvernorat');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Desk': MockDesk,
                'TraderCA': MockTraderCA,
                'TraderAGB': MockTraderAGB,
                'Gouvernorat': MockGouvernorat
            };
            createController = function() {
                $injector.get('$controller')("DeskDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:deskUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
