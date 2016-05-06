angular.module('myApp')

    .controller('buscarCtrl', [ '$scope', '$state', 'auth', 'buscarService', 'videojuegoService', function($scope,$state,auth,buscarService,videojuegoService){
        $scope.listaVideojuegos;
        $scope.busqueda = "";
        $scope.listaGenero = ["MMORPG","Survival horror","Acción-Aventura"];
        $scope.genero = "";
        $scope.listaPlataforma = ["PC","PlayStation 3","PlayStation 4"];
        $scope.plataforma = "";
        $scope.listaValoraciones = [false,false,false,false,false];
        $scope.valoracion = "";
        
        $scope.buscar = function(){
            buscarService.buscar(false, function(videojuegos) {
                $scope.listaVideojuegos = videojuegos;
            });
        };
        $scope.buscar();

        $scope.limpiarFiltros = function(){
            $scope.genero = "";
            $scope.plataforma = "";
            $scope.listaValoraciones = [false,false,false,false,false];
            $scope.valoracion = "";
        };

        $scope.filtroValoracion = function (num) {
            for (i = 0; i<5;i++) {
                if (i < num) {
                    $scope.listaValoraciones[i] = true;
                } else{
                    $scope.listaValoraciones[i] = false;
                }
            }
            switch (num) {
                case 1:
                    $scope.valoracion = "unaEstrella";
                    break;
                case 2:
                    $scope.valoracion = "dosEstrella";
                    break;
                case 3:
                    $scope.valoracion = "tresEstrella";
                    break;
                case 4:
                    $scope.valoracion = "cuatroEstrella";
                    break;
                default:
                    $scope.valoracion = "cincoEstrella";
            }
        };
        
        $scope.estrellaOn = function (num) {
            return $scope.listaValoraciones[num-1];
        };

        $scope.entrarVideojuego = function (id) {
            videojuegoService.setVideojuego(id);
            $state.go('videojuego');
        }

    }]);
