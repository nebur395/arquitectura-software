angular.module('myApp')

    .controller('novedadesCtrl', [ '$scope', '$state', 'auth', 'novedadesService', 'videojuegoService', function($scope,$state,auth,novedadesService,videojuegoService){
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

        $scope.entrarVideojuego = function (id) {
            videojuegoService.setVideojuego(id);
            $state.go('videojuego');
        }
        
    }]);
