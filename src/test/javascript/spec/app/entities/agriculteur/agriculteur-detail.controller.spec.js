'use strict';

describe('Controller Tests', function() {

    describe('Agriculteur Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAgriculteur, MockProfil, MockActiviteAgricole, MockSpecialiteAgricole, MockCommandeDetails, MockCommande, MockProduction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            MockProfil = jasmine.createSpy('MockProfil');
            MockActiviteAgricole = jasmine.createSpy('MockActiviteAgricole');
            MockSpecialiteAgricole = jasmine.createSpy('MockSpecialiteAgricole');
            MockCommandeDetails = jasmine.createSpy('MockCommandeDetails');
            MockCommande = jasmine.createSpy('MockCommande');
            MockProduction = jasmine.createSpy('MockProduction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Agriculteur': MockAgriculteur,
                'Profil': MockProfil,
                'ActiviteAgricole': MockActiviteAgricole,
                'SpecialiteAgricole': MockSpecialiteAgricole,
                'CommandeDetails': MockCommandeDetails,
                'Commande': MockCommande,
                'Production': MockProduction
            };
            createController = function() {
                $injector.get('$controller')("AgriculteurDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:agriculteurUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
