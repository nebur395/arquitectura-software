angular.module('myApp')

    .controller('novedadesCtrl', [ '$scope','auth', 'novedadesService', function($scope,auth,novedadesService){
        $scope.mostrar = ["Comentarios","Valoraciones"];
        $scope.novedades = "Comentarios";
        $scope.novedadesComentarios;
        $scope.novedadesValoraciones;

        $scope.cogerNovedades = function(){
            novedadesService.novedades( auth.idUser(), function(comentarios,valoraciones) {
                $scope.novedadesComentarios = comentarios;
                $scope.novedadesValoraciones = valoraciones;
            });
        };
        $scope.cogerNovedades();
        
    }]);
