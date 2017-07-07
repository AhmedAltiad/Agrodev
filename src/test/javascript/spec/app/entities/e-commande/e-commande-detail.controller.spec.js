'use strict';

describe('Controller Tests', function() {

    describe('ECommande Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockECommande, MockAnnonce, MockProfil, MockTransaction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockECommande = jasmine.createSpy('MockECommande');
            MockAnnonce = jasmine.createSpy('MockAnnonce');
            MockProfil = jasmine.createSpy('MockProfil');
            MockTransaction = jasmine.createSpy('MockTransaction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ECommande': MockECommande,
                'Annonce': MockAnnonce,
                'Profil': MockProfil,
                'Transaction': MockTransaction
            };
            createController = function() {
                $injector.get('$controller')("ECommandeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:eCommandeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
