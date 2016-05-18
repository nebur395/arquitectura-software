angular.module('myApp')

    .controller('novedadesCtrl', ['$scope', '$state', 'auth', 'novedadesService', 'videojuegoService', 'usuarioService', function ($scope, $state, auth, novedadesService, videojuegoService, usuarioService) {
        $scope.mostrar = ["Comentarios", "Valoraciones"];
        $scope.novedades = "Comentarios";
        $scope.novedadesComentarios;
        $scope.novedadesValoraciones;

        $scope.cogerNovedades = function () {
            novedadesService.novedades(auth.idUser(), function (comentarios, valoraciones) {
                $scope.novedadesComentarios = comentarios;
                $scope.novedadesValoraciones = valoraciones;
            });
        };
        $scope.cogerNovedades();

        $scope.entrarVideojuego = function (id) {
            videojuegoService.setVideojuego(id);
            $state.go('videojuego');
        };

        $scope.entrarUsuario = function (id) {
            usuarioService.setUsuario(id);
            $state.go('usuario');
        };

    }]);
