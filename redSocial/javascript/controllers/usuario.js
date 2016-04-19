angular.module('myApp')

    .controller('usuarioCtrl', ['$scope','$state','usuarioService','videojuegoService',function($scope,$state,usuarioService,videojuegoService){
        $scope.mostrar = ["Comentarios","Valoraciones"];
        $scope.opinion = "Comentarios";
        $scope.listaComentarios;
        $scope.listaValoraciones;
        $scope.usuario;

        //gestion de boton seguir
        $scope.usuarioAjustes = false;
        $scope.usuarioNoSeguido = false;
        $scope.usuarioSeguido = false;


        $scope.cogerInfo = function(){
            usuarioService.getInfo(function(usuario,comentarios,valoraciones) {
                $scope.usuario = usuario;
                $scope.listaComentarios = comentarios;
                $scope.listaValoraciones = valoraciones;
            });
        };
        $scope.cogerInfo();

        $scope.elegirBoton = function () {
            switch ($scope.usuario.tipo) {
                case 1:
                    $scope.usuarioAjustes = true;
                    break;
                case 2:
                    $scope.usuarioNoSeguido = true;
                    break;
                default:
                    $scope.usuarioSeguido = true;
            }
        };
        $scope.elegirBoton();

        $scope.entrarVideojuego = function (id) {
            videojuegoService.setVideojuego(id);
            $state.go('videojuego');
        };

        $scope.irAjustes = function () {
            $state.go('ajustes');
        };
        
        $scope.seguir = function () {
            usuarioService.seguir($scope.usuario);
        };
        
        $scope.dejarSeguir = function () {
            usuarioService.dejarSeguir($scope.usuario);
        }
        
    }]);
