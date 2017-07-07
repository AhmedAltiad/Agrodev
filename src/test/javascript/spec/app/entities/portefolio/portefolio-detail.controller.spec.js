'use strict';

describe('Controller Tests', function() {

    describe('Portefolio Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPortefolio, MockTraderAGB, MockVariete;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPortefolio = jasmine.createSpy('MockPortefolio');
            MockTraderAGB = jasmine.createSpy('MockTraderAGB');
            MockVariete = jasmine.createSpy('MockVariete');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Portefolio': MockPortefolio,
                'TraderAGB': MockTraderAGB,
                'Variete': MockVariete
            };
            createController = function() {
                $injector.get('$controller')("PortefolioDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:portefolioUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
