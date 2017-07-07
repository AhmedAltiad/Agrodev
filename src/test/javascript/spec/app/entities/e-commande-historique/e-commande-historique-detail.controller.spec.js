'use strict';

describe('Controller Tests', function() {

    describe('ECommandeHistorique Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockECommandeHistorique, MockAnnonceHistorique, MockProfil, MockTransactionHistorique;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockECommandeHistorique = jasmine.createSpy('MockECommandeHistorique');
            MockAnnonceHistorique = jasmine.createSpy('MockAnnonceHistorique');
            MockProfil = jasmine.createSpy('MockProfil');
            MockTransactionHistorique = jasmine.createSpy('MockTransactionHistorique');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ECommandeHistorique': MockECommandeHistorique,
                'AnnonceHistorique': MockAnnonceHistorique,
                'Profil': MockProfil,
                'TransactionHistorique': MockTransactionHistorique
            };
            createController = function() {
                $injector.get('$controller')("ECommandeHistoriqueDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'agroBourse360SiApp:eCommandeHistoriqueUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
