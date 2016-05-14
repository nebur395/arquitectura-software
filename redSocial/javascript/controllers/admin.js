angular.module('myApp')

    .controller('adminCtrl', ['$scope', 'adminService', function ($scope, adminService) {

        $scope.listaGenero = ["MMORPG", "Survival horror", "Acción-Aventura"];
        $scope.genero = "";
        $scope.listaPlataforma = ["PC", "PlayStation 2", "PlayStation 3", 
            "PlayStation 4", "XboxOne", "Xbox 360", "Wii U"];
        $scope.plataforma = "";
        $scope.descripcion = "";
        $scope.lanzamiento = "";
        $scope.desarrolladora = "";
        $scope.distribuidora = "";
        $scope.nombre = "";

        $scope.errorAdmin = false;
        $scope.exitoAdmin = false;
        $scope.errorMsgAdmin = "";

        $scope.hideError = function () {
            $scope.errorMsgAdmin = "";
            $scope.errorAdmin = false;
        };

        $scope.hideExito = function () {
            $scope.exitoAdmin = false;
        };

        var showError = function (error) {
            $scope.errorMsgAdmin = error;
            $scope.errorAdmin = true;
        };

        var showExito = function () {
            $scope.exitoAdmin = true;
        };

        $scope.insertarVideojuego = function () {
            var videojuego = {
                nombre: $scope.nombre,
                distribuidora: $scope.distribuidora,
                desarrolladora: $scope.desarrolladora,
                lanzamiento: $scope.lanzamiento,
                descripcion: $scope.descripcion,
            }
            adminService.insertarVideojuego(videojuego, showExito, showError);
        };

    }]);
