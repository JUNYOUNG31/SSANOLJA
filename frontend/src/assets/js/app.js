const canvas = document.getElementById("js-paint");
const ctx = canvas.getContext("2d");

const strokeInput = document.getElementById("js-line"); //선 굵기 조절
const fillBtn = document.getElementById("js-fill");      // 채우기 버튼 클릭
const drawBtn = document.getElementById('js-draw');       // 그리기 버튼 클릭
const eraserBtn = document.getElementById('js-eraser'); //지우개 버튼 클릭
const resetBtn = document.getElementById('js-reset'); //전체 삭제 버튼 클릭
const areaBtn = document.getElementById('js-fillarea'); //구역 칠하기 버튼
const colors = document.getElementsByClassName("color");//색 캔버스



canvas.width = 1000;
canvas.height = 600;

ctx.lineWidth = 2.5;
ctx.strokeStyle = "#000000"

let x, y; //좌표 값
let painting = false; //그림 그리는 중인지
let filling = false; //채우기 사용중인지
let areaFilling = false;

// //마우스 움직임 감지
const onMouseMove = event => {
    x = event.offsetX;
    y = event.offsetY;

        if(!filling){
            if(!painting){          //painting 하는 중이 아닐 때
                ctx.beginPath();
                ctx.moveTo(x,y);
            }else{                  //painting하는 중 일 때
                ctx.lineTo(x,y);
                ctx.stroke();
            }
        }
}
    


//painting 시작 중지
const startPainting = () => (painting = true);
const stopPainting = () => (painting = false);

//filling 시작 끝
const startFilling = () =>  {
    filling = true;
    stopErasing();
    areaFilling = false;
}
const stopFilling = () => {
    stopErasing();
    filling = false;
    areaFilling = false;
}

//filling Area 시작 끝
const startAreaFilling = () => {
    areaFilling = true;
    stopFilling();
    stopPainting();
    stopErasing();
}

//캔버스 클릭 시 filling값 true면 filling 실행
const onCanvasClick = () => {
    if(filling){
        console.log("aaa");
        ctx.closePath();
        ctx.beginPath();
        ctx.fillStyle = ctx.strokeStyle;
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }else if(areaFilling){
        console.log("eee");
        ctx.beginPath();
        ctx.fillStyle = ctx.strokeStyle;
        ctx.closePath();
        ctx.fill();
    }
}

// //선 두께 변경
const onRangeChange = e => {
    const value = e.target.value;
    ctx.lineWidth = value;
}

//선택한 color클래스의 backgroundcolor로 strokeStyle 변경
const onColorClick = e => {
    const style = e.target.style;
    ctx.strokeStyle = style.backgroundColor;
}

const startErasing = () => {
    ctx.globalCompositeOperation = 'destination-out';//지우개 속성 사용
    filling = false; //채우기 속성이 true 이면 전체가 다 사라져버림
}
const stopErasing = () => {
    ctx.globalCompositeOperation = 'source-over'; //지우개 속성 없어짐
}

const resetAll = () => {
    ctx.clearRect(0,0,canvas.width, canvas.height);
}

//색 변경 이벤트
Array.from(colors).forEach(color =>
    color.addEventListener("click", onColorClick, false)
);



canvas.addEventListener("mousemove", onMouseMove, false);
canvas.addEventListener("mousedown", startPainting, false);
canvas.addEventListener("mouseup", stopPainting, false);
canvas.addEventListener("mouseleave", stopPainting, false);
canvas.addEventListener("click", onCanvasClick , false);

strokeInput.addEventListener("input", onRangeChange, false);// 선 굵기 조절
fillBtn.addEventListener("click", startFilling, false);     //채우기 버튼 클릭
drawBtn.addEventListener("click", stopFilling, false); //그림 그리기 버튼 클릭 시 채우기 버튼 stop
eraserBtn.addEventListener("click", startErasing, false);
resetBtn.addEventListener("click", resetAll, false);
areaBtn.addEventListener("click", startAreaFilling, false);
