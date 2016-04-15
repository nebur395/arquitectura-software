angular.module('myApp')

    .controller('videojuegoCtrl', [ '$scope', '$state', 'auth', 'videojuegoService', function($scope,$state,auth,videojuegoService){
        $scope.mostrar = ["Comentarios","Valoraciones"];
        $scope.novedades = "Comentarios";
        $scope.listaComentarios;
        $scope.listaValoraciones;
        $scope.videojuego;

        $scope.cogerInfo = function(){
            videojuegoService.getInfo(function(videojuego,comentarios,valoraciones) {
                $scope.videojuego = videojuego;
                $scope.listaComentarios = comentarios;
                $scope.listaValoraciones = valoraciones;
            });
        };
        $scope.cogerInfo();
        
        
    }]);
