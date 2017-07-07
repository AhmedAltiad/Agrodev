'use strict';

describe('Controller Tests', function() {

    describe('Profil Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfil, MockEmployee, MockEmployer, MockAgriculteur, MockTraderAGB, MockTraderCA, MockAnnonce, MockAnnonceHistorique, MockView, MockAnnonceChangement, MockECommande, MockECommandeHistorique, MockTransaction;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfil = jasmine.createSpy('MockProfil');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockEmployer = jasmine.createSpy('MockEmployer');
            MockAgriculteur = jasmine.createSpy('MockAgriculteur');
            MockTraderAGB = jasmine.createSpy('MockTraderAGB');
            MockTraderCA = jasmine.createSpy('MockTraderCA');
            MockAnnonce = jasmine.createSpy('MockAnnonce');
            MockAnnonceHistorique = jasmine.createSpy('MockAnnonceHistorique');
            MockView = jasmine.createSpy('MockView');
            MockAnnonceChangement = jasmine.createSpy('MockAnnonceChangement');
            MockECommande = jasmine.createSpy('MockECommande');
            MockECommandeHistorique = jasmine.createSpy('MockECommandeHistorique');
            MockTransaction = jasmine.createSpy('MockTransaction');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Profil': MockProfil,
                'Employee': MockEmployee,
                'Employer': MockEmployer,
                'Agriculteur': MockAgriculteur,
                'TraderAGB': MockTraderAGB,
                'TraderCA': MockTraderCA,
                'Annonce': MockAnnonce,
                'AnnonceHistorique': MockAnnonceHistorique,
                'View': MockView,
                'AnnonceChangement': MockAnnonceChangement,
                'ECommande': MockECommande,
                'ECommandeHistorique': MockECommandeHistorique,
                'Transaction': MockTransaction
            };
            createController = function() {
                $injector.get('$controller')("ProfilDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:profilUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
