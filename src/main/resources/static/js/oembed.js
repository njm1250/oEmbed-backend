
function requestUrl() {

    //const COMMON_URL = 'http://localhost:8080';
    const apiUrl = document.getElementById('search').value;
    alert("/response?url="+ apiUrl);
    fetch("/response?url="+ apiUrl)
    .then(res=>{
          res.text().then(function(text){
            console.log("text 안에 데이터 = " + text);
            alert(text);
          })
    })
    .catch(err=>{
        alert("ERROR!");
        console.log(err);
    })
    // 아닐 수도 있음 . URL(지금은 걍 바로 변환된 url주는중) 이제 여기서 fetch한 json값을 받아서 pasing 후 출력


}

