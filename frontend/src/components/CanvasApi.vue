<template>
<div id="gamearea">
                <div class="top">
                  <canvas ref="canvas" id="js-paint" 
                  @mousemove="onMouseMove"
                  @mousedown.left="startPainting"
                  @mouseup ="stopPainting"
                  @mouseleave="stopPainting"
                  @click.left="onMouseClick"
                  @contextmenu="handleCM"
                  width="700"
                  height="500"></canvas>
                    <div class="controls right">
                      <button @click="startFilling">
                        <svg width="70" height="70" viewBox="0 0 70 70" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M23.927 5.84473C27.6037 5.84473 30.6448 9.06366 31.1461 13.249L55.8897 37.9926L33.2034 60.679C29.7863 64.0961 24.2461 64.0961 20.829 60.679L8.45466 48.3046C5.03757 44.8875 5.03757 39.3473 8.45466 35.9303L16.6353 27.7496V14.4699C16.6353 9.70636 19.8999 5.84473 23.927 5.84473ZM25.3853 14.4699V18.9996L22.4686 21.9163V14.4699C22.4686 13.5172 23.1215 12.7449 23.927 12.7449C24.7324 12.7449 25.3853 13.5172 25.3853 14.4699ZM25.3853 31.7203V27.2492L12.5795 40.055C11.4404 41.1941 11.4404 43.0408 12.5795 44.1798L24.9538 56.5542C26.0928 57.6932 27.9396 57.6932 29.0786 56.5542L47.6402 37.9926L31.2186 21.5711V31.7203C31.2186 31.8458 31.2164 31.9706 31.2119 32.0947H25.3508C25.3734 31.9742 25.3853 31.8489 25.3853 31.7203ZM49.1711 49.2188C45.754 52.6359 45.754 58.1761 49.1711 61.5932C52.5882 65.0102 58.1284 65.0102 61.5455 61.5932C64.9626 58.1761 64.9626 52.6359 61.5455 49.2188L55.3583 43.0316L49.1711 49.2188Z" fill="black"/>
                        <path d="M23.927 5.84473C27.6037 5.84473 30.6448 9.06366 31.1461 13.249L55.8897 37.9926L33.2034 60.679C29.7863 64.0961 24.2461 64.0961 20.829 60.679L8.45466 48.3046C5.03757 44.8875 5.03757 39.3473 8.45466 35.9303L16.6353 27.7496V14.4699C16.6353 9.70636 19.8999 5.84473 23.927 5.84473ZM25.3853 14.4699V18.9996L22.4686 21.9163V14.4699C22.4686 13.5172 23.1215 12.7449 23.927 12.7449C24.7324 12.7449 25.3853 13.5172 25.3853 14.4699ZM25.3853 31.7203V27.2492L12.5795 40.055C11.4404 41.1941 11.4404 43.0408 12.5795 44.1798L24.9538 56.5542C26.0928 57.6932 27.9396 57.6932 29.0786 56.5542L47.6402 37.9926L31.2186 21.5711V31.7203C31.2186 31.8458 31.2164 31.9706 31.2119 32.0947H25.3508C25.3734 31.9742 25.3853 31.8489 25.3853 31.7203ZM49.1711 49.2188C45.754 52.6359 45.754 58.1761 49.1711 61.5932C52.5882 65.0102 58.1284 65.0102 61.5455 61.5932C64.9626 58.1761 64.9626 52.6359 61.5455 49.2188L55.3583 43.0316L49.1711 49.2188Z" stroke="black"/>
                        </svg>
                      </button>
                      <button @click="stopFilling">
                          <svg width="70" height="70" viewBox="0 0 70 70" fill="none" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink">
                          <rect width="70" height="70" fill="url(#pattern0)"/>
                          <defs>
                          <pattern id="pattern0" patternContentUnits="objectBoundingBox" width="1" height="1">
                          <use xlink:href="#image0_52_2268" transform="scale(0.00195312)"/>
                          </pattern>
                          <image id="image0_52_2268" width="512" height="512" xlink:href="data:image/png;base64,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"/>
                          </defs>
                          </svg>
                      </button>
                      <button @click="startErasing">
                        
                          지우개


                      </button>
                      <button @click="resetAll" >
                        <svg width="77" height="77" viewBox="0 0 77 77" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M32.0833 48.125L32.0833 38.5" stroke="black" stroke-width="3" stroke-linecap="round"/>
                        <path d="M44.9167 48.125L44.9167 38.5" stroke="black" stroke-width="3" stroke-linecap="round"/>
                        <path d="M9.625 22.458H67.375H61.75C59.8644 22.458 58.9216 22.458 58.3358 23.0438C57.75 23.6296 57.75 24.5724 57.75 26.458V60.1663C57.75 62.052 57.75 62.9948 57.1642 63.5806C56.5784 64.1663 55.6356 64.1663 53.75 64.1663H23.25C21.3644 64.1663 20.4216 64.1663 19.8358 63.5806C19.25 62.9948 19.25 62.052 19.25 60.1663V26.458C19.25 24.5724 19.25 23.6296 18.6642 23.0438C18.0784 22.458 17.1356 22.458 15.25 22.458H9.625Z" stroke="black" stroke-width="3" stroke-linecap="round"/>
                        <path d="M32.3021 10.814C32.6676 10.4729 33.4732 10.1715 34.5939 9.9565C35.7145 9.74152 37.0876 9.625 38.5001 9.625C39.9126 9.625 41.2857 9.74152 42.4063 9.9565C43.5269 10.1715 44.3325 10.4729 44.6981 10.814" stroke="black" stroke-width="3" stroke-linecap="round"/>
                        </svg>  
                      </button>
                      <button @click="canvasComplete" >완료</button>
                    </div>
                </div>

                <div class="bottom">
                  <div class="colors left"><!-- 색깔 정렬-->
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(0, 0, 0)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,255,255)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,59,48)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,149,0)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(255,204,0)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(76,217,100)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(90,200,250)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(0,122,255)"></div>
                    <div class="color" @click = "colorChange($event)" style="background-color:rgb(88,86,214)"></div>
                  </div>
                  <div class = "right inputarea">
                    <input type="range" id="js-line" min="0.1" max="20.0" step="any" value="5" @input="onRangeChange" /><!--굵기 조절-->
                  </div>
                </div>
  </div>
</template>


<script>

export default{
  

data:function(){
  return{
    canvas : document.getElementById("js-paint"),
    painting : false,
    filling : false,
    lineWidth :   2.5,
    strokeStyle : '#000000',
    colors : document.getElementsByClassName("color"),
  }
},
  methods:{
    canvasComplete() {
      this.$emit("canvasCompleted")
    },
    saveClick(){//그림 저장하는 코드
      this.image = this.$refs.canvas.toDataURL("image/jpeg");
      this.$emit("setDrawData", this.image)
      console.log(this.image);
    },
    handleCM(event){
      event.preventDefault();
    },
    onRangeChange(event){
      this.value = event.target.value;
      this.ctx.lineWidth = this.value;
    },
    colorChange(event){ 
      this.style = event.target.style;
      this.ctx.strokeStyle = this.style.backgroundColor;
    },
    onMouseMove(event){
        let x = event.offsetX;
        let y = event.offsetY;
        if(!this.filling){
          if(!this.painting){
                this.ctx.beginPath();
                this.ctx.moveTo(x,y);
          }else{
            this.ctx.lineTo(x,y);
            this.ctx.stroke();
          }
        }
      },
      onMouseClick(){
          if(this.filling){
              console.log(this.$refs.canvas.width)
              this.ctx.closePath();
              this.ctx.beginPath();
              this.ctx.fillStyle = this.ctx.strokeStyle;
              this.ctx.fillRect(0, 0, this.$refs.canvas.width, this.$refs.canvas.height);
          }
      },
      startPainting(){
        this.painting = true;
      },
      stopPainting(){
        this.painting = false;
      },
      stopFilling(){
        this.stopErasing();
        this.filling = false;
      },
      startFilling(){
        this.filling = true;
        this.stopErasing();
      },
      stopErasing(){
        this.ctx.globalCompositeOperation = 'source-over';
      },
      startErasing(){
        this.ctx.globalCompositeOperation = 'destination-out';
        this.filling = false;
      },
      resetAll(){
        this.ctx.clearRect(0,0,this.$refs.canvas.width, this.$refs.canvas.height);
        this.ctx.fillStyle='white';
         this.ctx.fillRect(0,0,this.$refs.canvas.width, this.$refs.canvas.height);
      },

    },
    mounted(){
        this.ctx = this.$refs.canvas.getContext('2d');
        this.ctx.fillStyle='white';
        this.ctx.fillRect(0,0,this.$refs.canvas.width, this.$refs.canvas.height);
        console.log("dkkdkdkdk"+ this.$refs.canvas.width, this.$refs.canvas.height )
    }

}



</script>
<style scoped>

.top .bottom{
  display: flex;
}



    .left{
      float : left;
    }
    
    .right{
      float : right;
    }

.controls{
      background-color: rgb(201, 201, 207);
      border-radius: 15px;
      margin-left : 25px;
     flex-direction: row;
     justify-content: center;

}

.controls > button{
    display : block;
    margin : 10px;
    width:77px;
    height : 77px;

    box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
    min-width: 50px;
    border-radius: 5px;
    border: 2px solid rgb(0, 0, 0);
    background-color: rgb(221, 221, 224);
    font-size: 14px;
    cursor: pointer;
    margin-bottom : 5px;
}

canvas {
  box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
  border-radius: 30px;
  background-color: white;
}

.color {
  height: 35px;
  width: 35px;
  margin: 4px 2px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
  border-radius: 25px;
}

.colors {
  display: flex;
}
/* 
button {
  box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
  border: 0px;
  min-width: 50px;
  border-radius: 5px;
  font-weight: 600;
  border: 2px solid rgb(201, 201, 207);
  color: rgb(221, 221, 224);
  font-size: 14px;
  cursor: pointer;
  margin-bottom : 5px;
} */

button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

.inputarea{

}

</style>