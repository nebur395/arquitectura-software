angular.module('myApp')

    .controller('buscarUsuarioCtrl', ['$scope', '$state', 'buscarService', 'usuarioService', function ($scope, $state, buscarService, usuarioService) {
        $scope.listaUsuarios;
        $scope.busqueda = "";

        $scope.buscar = function () {
            buscarService.buscar(true, function (usuarios) {
                $scope.listaUsuarios = usuarios;
            });
        };
        $scope.buscar();


        $scope.entrarUsuario = function (id) {
            usuarioService.setUsuario(id);
            $state.go('usuario');
        }

    }]);
