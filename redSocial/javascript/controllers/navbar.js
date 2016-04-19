angular.module('myApp')

    .controller('NavbarCtrl', [ '$scope', '$location', '$state', 'auth', function($scope,$location,$state,auth){
        
        $scope.idUsuario = "";
        
        $scope.sesionActiva = function() {
            if(auth.isAuthenticated()) {
                $scope.idUsuario = auth.identity();
                return true;
            } else {
                return false;
            }
        };

        $scope.isActive = function (viewLocation) {
            return viewLocation === $location.path();
        };

        $scope.salir = function (){
            localStorage.removeItem('videojuegoID');
            localStorage.removeItem('usuarioID');
            auth.authenticate(null);
            $state.go('initSesion');
        }
    }]);
