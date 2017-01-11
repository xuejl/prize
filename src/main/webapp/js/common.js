/**
 * Created by zf on 11/01/17.
 */
var app = angular.module('app', [])
app.controller('reset', function($scope, $http){
    $scope.resetData = function () {
        $http.get("app/resetData").success(function(response){
            if(response.errorCode == 200) {
                alert("重置成功.")
                history.go(0);
            }else {
                alert("重置失败, 请稍后再试;")
            }
        })
    }
})